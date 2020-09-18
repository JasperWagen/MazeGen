package services
import services.mazeSolver._
import org.scalatest.funsuite.AnyFunSuite

import models.mazeInfo.MazeDimensions

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

    test("06 convert searchPath to Array, 1x1 case"){
        val searchPath = List((0,0))
        val inputMaze: Array[Array[Int]] = Array(
            Array(1))

        val searchArr: Array[Array[Int]] = Array(
            Array(1))

        val mazeDimensions = MazeDimensions(1, 1)

        for (i <- inputMaze.indices) {
            for (j <- inputMaze(0).indices) {
                assert(depthFirstSearch.mapSearchPathToArr(searchPath, mazeDimensions)(i)(j) == searchArr(i)(j))
            }
        }
    }

    test("06 convert searchPath to Array, 2x2 case"){
        val searchPath = List((0,0), (0, 1))
        val inputMaze: Array[Array[Int]] =
            Array(Array(0, 0),
                  Array(0, 0))

        val searchArr: Array[Array[Int]] =
            Array(Array(1, 1),
                  Array(0, 0))

        val mazeDimensions = MazeDimensions(2, 2)

        for (i <- inputMaze.indices) {
            for (j <- inputMaze(0).indices) {
                assert(depthFirstSearch.mapSearchPathToArr(searchPath, mazeDimensions)(i)(j) == searchArr(i)(j))
            }
        }
    }

    test("06 convert searchPath to Array, large case"){
        val searchPath = List((0,0), (0, 1), (1, 1), (2, 1), (3,1), (3,2), (3,3), (3, 4), (3, 5))
        val inputMaze = Array(
            Array(1, 1, 0, 0, 0, 0),
            Array(0, 1, 1, 1, 0, 0),
            Array(0, 1, 0, 0, 0, 0),
            Array(0, 1, 1, 1, 1, 1),
            Array(0, 0, 0, 0, 0, 0))


        val searchArr =
            Array(
            Array(1, 1, 0, 0, 0, 0),
            Array(0, 1, 0, 0, 0, 0),
            Array(0, 1, 0, 0, 0, 0),
            Array(0, 1, 1, 1, 1, 1),
            Array(0, 0, 0, 0, 0, 0))

        val mazeDimensions = MazeDimensions(6, 5)


        for (i <- inputMaze.indices) {
            for (j <- inputMaze(0).indices) {
                assert(depthFirstSearch.mapSearchPathToArr(searchPath, mazeDimensions)(i)(j) == searchArr(i)(j))
            }
        }


    }





}
