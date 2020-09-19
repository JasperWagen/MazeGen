package services

import org.scalatest.funsuite.AnyFunSuite
import mazeGen.MazeGen
import models.mazeInfo.MazeDimensions

import scala.collection.mutable.ListBuffer

class MazeGenSpec extends AnyFunSuite{

    val mazeGen = new MazeGen
    test("test null canvas creation"){
        val mazeDimensions = MazeDimensions(0, 0)
        assert(mazeGen.canvas(mazeDimensions).length == 0)
    }

    test("test 3x2 canvas creation"){
        val mazeDimensions = MazeDimensions(3, 2)
        val testCanvas = mazeGen.canvas(mazeDimensions)
        assert(testCanvas.length == 2)
        assert(testCanvas(0).length == 3)
    }

    // -- Proximity Detection --
    test("proximity detection N false"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}

        assert(mazeGen.checkForPathProximity("north", (2, 1), inputMatrix).contains("north"))
    }

    test("proximity detection N true"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}
        inputMatrix(0)(0) = 1
        assert(mazeGen.checkForPathProximity("north", (2, 1), inputMatrix).isEmpty)
    }

    test("proximity detection E false"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}

        assert(mazeGen.checkForPathProximity("east", (1, 0), inputMatrix).contains("east"))
    }

    test("proximity detection E true"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}
        inputMatrix(0)(2) = 1
        assert(mazeGen.checkForPathProximity("east", (1, 0), inputMatrix).isEmpty)
    }

    test("proximity detection S false"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}

        assert(mazeGen.checkForPathProximity("south", (0, 1), inputMatrix).contains("south"))
    }

    test("proximity detection S true"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}
        inputMatrix(2)(2) = 1
        assert(mazeGen.checkForPathProximity("south", (0, 1), inputMatrix).isEmpty)
    }

    test("proximity detection W false"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}

        assert(mazeGen.checkForPathProximity("west", (1, 2), inputMatrix).contains("west"))
    }

    test("proximity detection W true"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}
        inputMatrix(0)(0) = 1
        assert(mazeGen.checkForPathProximity("west", (1, 2), inputMatrix).isEmpty)
    }

    test("proximity detection OOB exception"){
        val inputMatrix = Array.fill(3){Array.fill[Byte](3)(0)}
        inputMatrix(0)(0) = 1
        assert(mazeGen.checkForPathProximity("west", (1, 0), inputMatrix).isEmpty)
    }

    // --Movement Options--
    test("find movement options all true"){
        val inputMatrix = Array.fill(7){Array.fill[Byte](7)(0)}
        assert(mazeGen.moveOptions((3, 3), inputMatrix) == List("north", "east", "south", "west"))
    }

    test("find movement options N false"){
        val inputMatrix = Array.fill(5){Array.fill[Byte](5)(0)}
        inputMatrix(0)(3) = 1

        assert(mazeGen.moveOptions((2, 2), inputMatrix) == List("east", "south", "west"))
    }

    test("find movement options E false") {
        val inputMatrix = Array.fill(5){Array.fill[Byte](5)(0)}
        inputMatrix(2)(4) = 1

        assert(mazeGen.moveOptions((2, 2), inputMatrix) == List("north", "south", "west"))
    }

    test("find movement options S false") {
        val inputMatrix = Array.fill(5){Array.fill[Byte](5)(0)}
        inputMatrix(4)(2) = 1

        assert(mazeGen.moveOptions((2, 2), inputMatrix) == List("north", "east", "west"))
    }

    test("find movement options W false") {
        val inputMatrix = Array.fill(5){Array.fill[Byte](5)(0)}
        inputMatrix(3)(0) = 1

        assert(mazeGen.moveOptions((2, 2), inputMatrix) == List("north", "east", "south"))
    }

    test("find movement options OOB W") {
        val inputMatrix = Array.fill(5){Array.fill[Byte](5)(0)}

        assert(mazeGen.moveOptions((2, 1), inputMatrix) == List("north", "east", "south"))
    }
}
