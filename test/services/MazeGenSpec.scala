package services

import org.scalatest.funsuite.AnyFunSuite
import mazeGen.MazeGen

import scala.collection.mutable.ListBuffer

class MazeGenSpec extends AnyFunSuite{

    val mazeGen = new MazeGen
    test("test null canvas creation"){
        assert(mazeGen.canvas(0, 0).length == 0)
        assert(mazeGen.canvas(0, 0).length == 0)
    }

    test("test 3x2 canvas creation"){
        val testCanvas = mazeGen.canvas(3, 2)
        assert(testCanvas.length == 2)
        assert(testCanvas(0).length == 3)
    }

    // -- Proximity Detection --
    test("proximity detection N false"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        assert(!mazeGen.checkProximity(0, 1, 2, inputMatrix))
    }

    test("proximity detection N true"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(0)(0) = 1
        assert(mazeGen.checkProximity(0, 1, 2, inputMatrix))
    }

    test("proximity detection E false"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }

        assert(!mazeGen.checkProximity(1, 0, 1, inputMatrix))
    }

    test("proximity detection E true"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(0)(2) = 1
        assert(mazeGen.checkProximity(1, 0, 1, inputMatrix))
    }

    test("proximity detection S false"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }

        assert(!mazeGen.checkProximity(2, 1, 0, inputMatrix))
    }

    test("proximity detection S true"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(2)(2) = 1
        assert(mazeGen.checkProximity(2, 1, 0, inputMatrix))
    }

    test("proximity detection W false"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }

        assert(!mazeGen.checkProximity(3, 2, 1, inputMatrix))
    }

    test("proximity detection W true"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(0)(0) = 1
        assert(mazeGen.checkProximity(3, 2, 1, inputMatrix))
    }

    test("proximity detection OOB exception"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](3,3)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(0)(0) = 1
        assert(mazeGen.checkProximity(3, 2, 0, inputMatrix))
    }

    // --Movement Options--
    test("find movement options all true"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](7,7)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        assert(mazeGen.moveOptions(3, 3, inputMatrix) == ListBuffer(0, 1, 2, 3))
    }

    test("find movement options N false"){
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](5,5)
        for(i <- inputMatrix.indices){
            for(j <- inputMatrix(0).indices){
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(0)(3) = 1

        assert(mazeGen.moveOptions(2, 2, inputMatrix) == ListBuffer(1, 2, 3))
    }

    test("find movement options E false") {
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](5, 5)
        for (i <- inputMatrix.indices) {
            for (j <- inputMatrix(0).indices) {
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(2)(4) = 1

        assert(mazeGen.moveOptions(2, 2, inputMatrix) == ListBuffer(0, 2, 3))
    }

    test("find movement options S false") {
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](5, 5)
        for (i <- inputMatrix.indices) {
            for (j <- inputMatrix(0).indices) {
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(4)(2) = 1

        assert(mazeGen.moveOptions(2, 2, inputMatrix) == ListBuffer(0, 1, 3))
    }

    test("find movement options W false") {
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](5, 5)
        for (i <- inputMatrix.indices) {
            for (j <- inputMatrix(0).indices) {
                inputMatrix(i)(j) = 0
            }
        }
        inputMatrix(3)(0) = 1

        assert(mazeGen.moveOptions(2, 2, inputMatrix) == ListBuffer(0, 1, 2))
    }

    test("find movement options OOB W") {
        val inputMatrix: Array[Array[Int]] = Array.ofDim[Int](5, 5)
        for (i <- inputMatrix.indices) {
            for (j <- inputMatrix(0).indices) {
                inputMatrix(i)(j) = 0
            }
        }

        assert(mazeGen.moveOptions(1, 2, inputMatrix) == ListBuffer(0, 1, 2))
    }
}
