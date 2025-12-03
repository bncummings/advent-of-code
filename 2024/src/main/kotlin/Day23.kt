import java.io.File

typealias Graph = HashMap<String, HashSet<String>>

/**
 * Reads the input file and parses it into a list of edges (pairs of strings).
 * Each line in the file is expected to contain an edge in the format "node1-node2".
 * @param filename The name of the input file.
 * @return A mutable list of pairs, where each pair represents an edge in the graph.
 */
fun day23Input(filename: String): MutableList<Pair<String, String>> {
    val edgeList: MutableList<Pair<String, String>> = mutableListOf()
    File(filename).forEachLine {
        val edge = it.split("-")
        assert(edge.size == 2)
        edgeList.add(Pair(
            edge[0], edge[1]
        ))
    }
    return edgeList
}

/**
 * Initializes a graph represented as an adjacency list from a list of edges.
 * @param edges A list of pairs, where each pair represents an edge in the graph.
 * @return A graph represented as a HashMap, where the keys are node labels and the values are sets of neighboring nodes.
 */
fun initialiseGraph(edges: List<Pair<String, String>>): Graph {
    val graph: Graph = HashMap()
    edges.forEach {
        graph.getOrPut(it.first) { HashSet() }.add(it.second)
        graph.getOrPut(it.second) { HashSet() }.add(it.first)
    }
    return graph
}

/**
 * Finds all unique cycles of length n in the given graph.
 * @param graph The graph represented as an adjacency list.
 * @param n The desired length of the cycles to find.
 * @return A mutable set of sets, where each inner set represents a unique cycle.
 */
fun findAllNCycles(graph: Graph, n: Int): MutableSet<Set<String>> {
    val cycles: MutableSet<Set<String>> = mutableSetOf()
    val marked: HashSet<String> = HashSet()

    fun dfs(node: String, i: Int, currentList: List<String>, root: String) {
        marked.add(node)
        if (i == 0) {
            /* Unmark the node */
            marked.remove(node)
            if (root in graph[node]!!) {
                /* Found a cycle */
                cycles.add(currentList.plus(node).toHashSet())
            }
            return
        }
        for (neighbour in graph[node]!!) {
            if (!marked.contains(neighbour)) {
                dfs(neighbour, i - 1, currentList.plus(node), root)
            }
        }
        /* Make the node usable again */
        marked.remove(node)
    }

    /* Start searching from each node as the root */
    for (root in graph.keys) {
        dfs(root, n - 1, emptyList(), root)
    }
    return cycles
}

/**
 * Filters the set of cycles to find only those that contain at least one node starting with the letter 't'.
 * @param lanParties A set of cycles, where each cycle is represented as a set of nodes.
 * @return A set of cycles that contain at least one node starting with 't'.
 */
fun findTs(lanParties: Set<Set<String>>): Set<Set<String>> =
    lanParties.filter { set ->
        set.any { it[0] == 't' }
    }.toHashSet()

fun main() {
    val graph = initialiseGraph(day23Input("src/main/resources/Day23.txt"))
    println(findTs(findAllNCycles(graph, 3)).size)
}
