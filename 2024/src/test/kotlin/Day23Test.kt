import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    @Test
    fun `findAllNCycles() prints a sterile cycle`() {
        val graph: Graph = initialiseGraph(
            listOf(
                Pair("a", "b"),
                Pair("a", "c"),
                Pair("b", "c")
            )
        )
        assertEquals(
            setOf(
                setOf("a","b","c"),
            ),
            findAllNCycles(graph, 3)
        )
    }

    @Test
    fun `findAllNCycles() prints all cycles of length 3`() {
        val graph: Graph = initialiseGraph(day23Input(
            "src/test/resources/Day23.txt"
        ))
        println(graph)
        assertEquals(
            setOf(
                setOf("aq","cg","yn"),
                setOf("aq","vc","wq"),
                setOf("co","de","ka"),
                setOf("co","de","ta"),
                setOf("co","ka","ta"),
                setOf("de","ka","ta"),
                setOf("kh","qp","ub"),
                setOf("qp","td","wh"),
                setOf("tb","vc","wq"),
                setOf("tc","td","wh"),
                setOf("td","wh","yn"),
                setOf("ub","vc","wq"),
            ),
            findAllNCycles(graph, 3)
        )
    }

    @Test
    fun `findTs() returns all the sets with names containing 't'`() {
        val unfilteredSets = setOf(
            setOf("aq","cg","yn"),
            setOf("aq","vc","wq"),
            setOf("co","de","ka"),
            setOf("co","de","ta"),
            setOf("co","ka","ta"),
            setOf("de","ka","ta"),
            setOf("kh","qp","ub"),
            setOf("qp","td","wh"),
            setOf("tb","vc","wq"),
            setOf("tc","td","wh"),
            setOf("td","wh","yn"),
            setOf("ub","vc","wq"),
        )

        val filteredSets = setOf(
            setOf("co","de","ta"),
            setOf("co","ka","ta"),
            setOf("de","ka","ta"),
            setOf("qp","td","wh"),
            setOf("tb","vc","wq"),
            setOf("tc","td","wh"),
            setOf("td","wh","yn"),
        )
        assertEquals(
            filteredSets,
            findTs(unfilteredSets)
        )
    }

}