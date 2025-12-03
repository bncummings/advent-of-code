import java.io.File
import kotlin.math.pow

/**
 * Regex for matching leaf node definitions (e.g., "a: 0" or "b: 1").
 */
val LEAF_MATCHER = Regex("[a-z0-9]+: [01]")
val EXPR_MATCHER = Regex("[a-z0-9]+ (AND|OR|XOR) [a-z0-9]+ -> [a-z0-9]+")
val LABEL_MATCHER = Regex("[a-z0-9]+")
val OP_MATCHER = Regex("(AND|OR|XOR)")

/**
 * Represents a node in the computation graph, which can either be a leaf or an operation node.
 */
interface Node {
    fun generateValue(): Boolean
}

/**
 * Represents an operation node that computes its value based on its children and a logical operator.
 * @property value the cached Boolean value of the node, or `null` if not yet computed.
 * @property operator the logical operation to apply to the left and right children.
 * @property left the left child node.
 * @property right the right child node.
 */
data class OpNode(
    var value: Boolean? = null,
    var operator: (Boolean, Boolean) -> Boolean = { _, _ -> value!! },
    var left: Node? = null,
    var right: Node? = null
) : Node {
    /**
     * Computes and caches the Boolean value of this node based on its children and operator.
     * @return the computed Boolean value.
     * @throws NullPointerException if either `left` or `right` is not set.
     */
    override fun generateValue(): Boolean {
        try {
            if (value == null) {
                value = operator.invoke(
                    this.left!!.generateValue(), this.right!!.generateValue()
                )
            }
            return value!!
        } catch (e: NullPointerException) {
            error("left or right node doesn't exist: $e")
        }
    }
}

/**
 * Represents a leaf node in the computation graph, which holds a constant Boolean value.
 * @property value the Boolean value of the leaf node.
 */
data class LeafNode(var value: Boolean) : Node {
    override fun generateValue(): Boolean = value
}

/**
 * Initializes a computation graph from a file.
 * @param filename the path to the file containing the graph definition.
 * @return a map where keys are node labels and values are `Node` instances.
 * @throws AssertionError if an invalid expression is found in the file.
 */
fun initialiseGraph(filename: String): HashMap<String, Node> {
    val map: HashMap<String, Node> = hashMapOf()
    File(filename).forEachLine {
        if (LEAF_MATCHER.matches(it)) {
            /* Add the base cases to the map */
            map[it.take(3)] = LeafNode(value = it.drop(5) == "1")
        } else if (EXPR_MATCHER.matches(it)) {
            val labelList = LABEL_MATCHER.findAll(it).map(MatchResult::value).toList()
            assert(labelList.size == 3)

            val curNode = map.getOrPut(labelList[2]) { OpNode() } as OpNode
            curNode.operator = when (OP_MATCHER.find(it)?.value) {
                "AND" -> { x, y -> x && y }
                "OR" -> { x, y -> x || y }
                else -> { x, y -> x xor y }
            }
            /* Add the child nodes to the map */
            curNode.left = map.getOrPut(labelList[0]) { OpNode() }
            curNode.right = map.getOrPut(labelList[1]) { OpNode() }
        }
    }

    /* Compute values for all nodes */
    for (node in map.values) {
        if (node is OpNode) {
            node.generateValue()
        }
    }

    return map
}

/**
 * Filters and retrieves nodes with labels starting with "z" from the graph.
 * @param map the graph as a map of labels to nodes.
 * @return a sorted map of nodes whose labels start with "z".
 */
fun getZs(map: Map<String, Node>): Map<String, Node> =
    map.filterKeys { it.startsWith("z") }
        .toSortedMap()

/**
 * Computes the final output value based on the nodes with labels starting with "z".
 * @param map the graph as a map of labels to nodes.
 * @return the computed output value as a `Long`.
 */
fun getOutput(map: Map<String, Node>): Long {
    var total = 0L
    getZs(map).forEach { (k, v) ->
        if (v.generateValue()) {
            total += 2.0.pow(k.drop(1).toInt()).toLong()
        }
    }
    return total
}

fun main() {
    val map = initialiseGraph("src/main/resources/Day24.txt")
    println(map.size)
    println(getZs(map).keys)
    println(getOutput(map))
}
