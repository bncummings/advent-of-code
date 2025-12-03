import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {
    @Test
    fun `gets correct input`() {
        val dummyGrid = listOf("##########" ,
            "#..O..O.O#" ,
            "#......O.#" ,
            "#.OO..O.O#" ,
            "#..O@..O.#" ,
            "#O#..O...#" ,
            "#O..O..O.#" ,
            "#.OO.O.OO#" ,
            "#....O...#" ,
            "##########")

        val dummyInstructions = "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^" +
                "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v" +
                "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<" +
                "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^" +
                "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><" +
                "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^" +
                ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^" +
                "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>" +
                "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>" +
                "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^"

        val test = questionFifteen("src/test/resources/DayFifteenLarge.txt")

        assertEquals(
            dummyInstructions.toList(),
            test.instructions
        )

        assertEquals(
            dummyGrid.map { it.toList() },
            test.grid
        )
        
    }

    @Test
    fun `sumBoxCoordinates() correctly evaluates the coordinates`() {
        val grid = listOf(
            "##########" ,
            "#.O.O.OOO#" ,
            "#........#" ,
            "#OO......#" ,
            "#OO@.....#" ,
            "#O#.....O#" ,
            "#O.....OO#" ,
            "#O.....OO#" ,
            "#OO....OO#" ,
            "##########")
            .map { it.toList() }

        assertEquals(
            10092,
            sumBoxCoordinates(grid)
        )
    }

    @Test
    fun `getRobotCoordinates() returns the position of the robot`() {
        val grid = listOf(
            "##########" ,
            "#.O.O.OOO#" ,
            "#........#" ,
            "#OO......#" ,
            "#OO@.....#" ,
            "#O#.....O#" ,
            "#O.....OO#" ,
            "#O.....OO#" ,
            "#OO....OO#" ,
            "##########")
            .map { it.toList() }

        assertEquals(
            Pair(4, 3),
            getRobotCoordinates(grid)
        )
    }

    @Test
    fun `moveCharacter() moves a robot when there's nothing in the way`() {
        val grid = listOf(
            "##########" ,
            "#.O.O.OOO#" ,
            "#........#" ,
            "#OO......#" ,
            "#OO@.....#" ,
            "#O#.....O#" ,
            "#O.....OO#" ,
            "#O.....OO#" ,
            "#OO....OO#" ,
            "##########")
            .map { it.toMutableList() }

        moveCharacter(4, 3, Direction.UP, grid)
        assertEquals(
            listOf(
                "##########" ,
                "#.O.O.OOO#" ,
                "#........#" ,
                "#OO@.....#" ,
                "#OO......#" ,
                "#O#.....O#" ,
                "#O.....OO#" ,
                "#O.....OO#" ,
                "#OO....OO#" ,
                "##########")
                .map { it.toList() },

            grid
        )

    }

    @Test
    fun `moveCharacter() does not move robot when there's a block`() {
        val grid = listOf(
            "##########" ,
            "#.O.O.OOO#" ,
            "#........#" ,
            "#OO......#" ,
            "#OO@.....#" ,
            "#O#.....O#" ,
            "#O.....OO#" ,
            "#O.....OO#" ,
            "#OO....OO#" ,
            "##########")
            .map { it.toMutableList() }

        moveCharacter(4, 3, Direction.LEFT, grid)
        assertEquals(
            listOf(
                "##########" ,
                "#.O.O.OOO#" ,
                "#........#" ,
                "#OO......#" ,
                "#OO@.....#" ,
                "#O#.....O#" ,
                "#O.....OO#" ,
                "#O.....OO#" ,
                "#OO....OO#" ,
                "##########")
                .map { it.toList() },

            grid
        )

    }

    @Test
    fun `moveCharacter() allows robot to push one block`() {
        val grid = listOf(
            "##########" ,
            "#.O.O.OOO#" ,
            "#.@......#" ,
            "#OO......#" ,
            "#O.......#" ,
            "#O......O#" ,
            "#O.....OO#" ,
            "#O.....OO#" ,
            "#OO....OO#" ,
            "##########")
            .map { it.toMutableList() }

        moveCharacter(2, 2, Direction.DOWN, grid)
        assertEquals(
            listOf(
                "##########" ,
                "#.O.O.OOO#" ,
                "#........#" ,
                "#O@......#" ,
                "#OO......#" ,
                "#O......O#" ,
                "#O.....OO#" ,
                "#O.....OO#" ,
                "#OO....OO#" ,
                "##########")
                .map { it.toList() },

            grid
        )

    }

    @Test
    fun `moveCharacter() allows robot to push many blocks`() {
        val grid = listOf(
            "##########" ,
            "#.O.O.OOO#" ,
            "#.@......#" ,
            "#OO......#" ,
            "#OO......#" ,
            "#O......O#" ,
            "#O.....OO#" ,
            "#O.....OO#" ,
            "#OO....OO#" ,
            "##########")
            .map { it.toMutableList() }

        moveCharacter(2, 2, Direction.DOWN, grid)
        assertEquals(
            listOf(
                "##########" ,
                "#.O.O.OOO#" ,
                "#........#" ,
                "#O@......#" ,
                "#OO......#" ,
                "#OO.....O#" ,
                "#O.....OO#" ,
                "#O.....OO#" ,
                "#OO....OO#" ,
                "##########")
                .map { it.toList() },

            grid
        )

    }

    @Test
    fun `simulatePacking() correctly `() {
        val input = questionFifteen("src/test/resources/DayFifteenSmall.txt")
        val startingPos = getRobotCoordinates(input.grid)!!

        simulatePacking(
            startingPos.first,
            startingPos.second,
            input)

        assertEquals(
            listOf(
                "########",
                "#....OO#",
                "##.....#",
                "#.....O#",
                "#.#O@..#",
                "#...O..#",
                "#...O..#",
                "########")
                .map { it.toList() },

            input.grid
        )
    }

    @Test
    fun `widenGrid() replaces each square with one twice as wide` (){
        val grid = listOf("##########" ,
            "#..O..O.O#" ,
            "#......O.#" ,
            "#.OO..O.O#" ,
            "#..O@..O.#" ,
            "#O#..O...#" ,
            "#O..O..O.#" ,
            "#.OO.O.OO#" ,
            "#....O...#" ,
            "##########").map{ it.toMutableList() }
        
        assertEquals(
            listOf(
                "####################",
                "##....[]....[]..[]##",
                "##............[]..##",
                "##..[][]....[]..[]##",
                "##....[]@.....[]..##",
                "##[]##....[]......##",
                "##[]....[]....[]..##",
                "##..[][]..[]..[][]##",
                "##........[]......##",
                "####################"
            ).map { it.toMutableList()},
            widenGrid(grid)
        )

    }
}