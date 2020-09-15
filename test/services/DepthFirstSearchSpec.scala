package services
import services.mazeSolver._
import org.scalatest.funsuite.AnyFunSuite

class DepthFirstSearchSpec extends AnyFunSuite{
    val depthFirstSearch = new DepthFirstSearch
    test("00 search, check null case"){
        val inputMaze: Array[Array[Int]] = Array(Array())
        assert(depthFirstSearch.search(inputMaze) == List())
    }

    test("01 search, check 2x2, bottom"){
        val inputMaze = Array(
            Array(0, 0),
            Array(1, 1))

        assert(depthFirstSearch.search(inputMaze) == List((1, 0), (1, 1)))
    }

    test("02 search, check 2x2, top"){
        val inputMaze = Array(
            Array(1, 1),
            Array(0, 0))

        assert(depthFirstSearch.search(inputMaze) == List((0, 0), (0, 1)))
    }

    test("03 search, check 3x3, (0, 0) to (1, 2)"){
        val inputMaze = Array(
            Array(1, 1, 0),
            Array(0, 1, 1),
            Array(0, 0, 0))

        assert(depthFirstSearch.search(inputMaze) == List((0, 0), (0, 1), (1, 1), (1, 2)))
    }

    test("04 search, check 6x5, forked path"){
        val inputMaze = Array(
            Array(1, 1, 0, 0, 0, 0),
            Array(0, 1, 1, 1, 0, 0),
            Array(0, 1, 0, 0, 0, 0),
            Array(0, 1, 1, 1, 1, 1),
            Array(0, 0, 0, 0, 0, 0))

        assert(depthFirstSearch.search(inputMaze).reverse.head == (3, 5))
    }

    test("05 search, check 10x9, forked path"){
        val inputMaze: Array[Array[Int]] =
            Array(
                Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                Array(0, 1, 0, 1, 1, 0, 1, 1, 1, 1),
                Array(0, 1, 0, 0, 1, 0, 1, 0, 0, 0),
                Array(1, 1, 1, 1, 1, 0, 1, 0, 1, 0),
                Array(0, 0, 1, 0, 0, 0, 1, 0, 1, 0),
                Array(0, 1, 1, 0, 1, 1, 1, 0, 1, 0),
                Array(0, 1, 0, 0, 1, 0, 1, 0, 1, 0),
                Array(0, 1, 1, 1, 1, 0, 1, 1, 1, 0),
                Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0))

        assert(depthFirstSearch.search(inputMaze).reverse.head == (1, 9))
    }





}
