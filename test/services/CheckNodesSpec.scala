package services
import services.mazeSolver._
import org.scalatest.funsuite.AnyFunSuite

class CheckNodesSpec extends AnyFunSuite{
    val checkNodes = new CheckNodes
    val mockMaze: Array[Array[Int]] = Array(Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                            Array(0, 1, 0, 1, 1, 0, 1, 1, 1, 1),
                                            Array(0, 1, 0, 0, 1, 0, 1, 0, 0, 0),
                                            Array(1, 1, 1, 1, 1, 0, 1, 0, 1, 0),
                                            Array(0, 0, 1, 0, 0, 0, 1, 0, 1, 0),
                                            Array(0, 1, 1, 0, 1, 1, 1, 0, 1, 0),
                                            Array(0, 1, 0, 0, 1, 0, 1, 0, 1, 0),
                                            Array(0, 1, 1, 1, 1, 0, 1, 1, 1, 0),
                                            Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0))

    test("01 find entrance"){
        val entranceLoc = checkNodes.findEntrance(mockMaze)
        assert(entranceLoc == (3, 0))
    }

    test("02 entrance not found error"){
        mockMaze(3)(0) = 0
        assertThrows[Exception]{
            checkNodes.findEntrance(mockMaze)
        }
    }

    test("03 check available nodes null, no visited"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)

        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List())
    }

    test("04 check available nodes north, no visited"){
        val inputMaze = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((0, 1)))
    }

    test("05 check available nodes east, no visited"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 1, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((1, 2)))
    }

    test("06 check available nodes south, no visited"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 1, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((2, 1)))
    }

    test("07 check available nodes north, east, no visited"){
        val inputMaze = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 1, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((0, 1), (1, 2)))
    }

    test("08 check available nodes north, south, west no visited"){
        val inputMaze = Array(
            Array(0, 1, 0, 0),
            Array(1, 1, 0, 1),
            Array(0, 1, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((0, 1), (2, 1), (1, 0)))
    }

    test("09 check visited nodes true, no visited"){
        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkVisitedNodes(visitedNodes,  position)  == None)
    }

    test("10 check visited nodes false"){
        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(0, 0, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkVisitedNodes(visitedNodes,  position) == Some(1, 1))
    }

    test("11 check possible nodes true"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 1, 0, 0))

        val position = (1, 1)
        val delta = (1, 0)
        assert(checkNodes.checkPossibleNodes(inputMaze, position, delta) == Some(2, 1))
    }

    test("12 check possible nodes false"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        val delta = (1, 0)
        assert(checkNodes.checkPossibleNodes(inputMaze, position, delta).isEmpty)
    }

    test("13 check available nodes: n; visited: n"){
        val inputMaze = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List())
    }

    test("14 check available nodes: n, e; visited: n"){
        val inputMaze = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 1, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(
            inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((1, 2)))
    }

    test("15 check available nodes: n; visited: n"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(1, 1, 1, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 1, 0, 0),
            Array(0, 1, 1, 0),
            Array(0, 0, 0, 0))

        val position = (1, 1)
        assert(checkNodes.checkAvailableNodes(
            inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List((1, 0)))
    }

    test("16 check available nodes OOB exception"){
        val inputMaze = Array(
            Array(0, 0, 0, 0),
            Array(1, 0, 0, 0),
            Array(0, 0, 0, 0))

        val visitedNodes = Array(
            Array(0, 0, 0, 0),
            Array(1, 0, 0, 0),
            Array(0, 0, 0, 0))

        val position = (1, 0)
        assert(checkNodes.checkAvailableNodes(
            inputMaze: Array[Array[Int]], visitedNodes: Array[Array[Int]], position: (Int, Int)) == List())
    }




}
