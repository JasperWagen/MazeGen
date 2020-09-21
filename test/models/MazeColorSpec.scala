package models

import org.scalatest.funsuite.AnyFunSuite

import models.mazeInfo.MazeColor

class MazeColorSpec extends AnyFunSuite{
    val bgHexColor = "#fcba01"
    val fgHexColor = "#fcba02"
    val solutionHexColor = "#fcba03"

    val mazeColor = new MazeColor(bgHexColor, fgHexColor, solutionHexColor)

    test("Maze Color returns bgHexColor"){
        assert(mazeColor.bgHexColor == "#fcba01")
    }

    test("Maze Color returns fgHexColor"){
        assert(mazeColor.fgHexColor == "#fcba02")
    }

    test("Maze Color returns solutionHexColor"){
        assert(mazeColor.solutionHexColor == "#fcba03")
    }

    test("Maze Color returns bgIntColor"){
        assert(mazeColor.bgIntColor == 16562689)
    }

    test("Maze Color returns fgIntColor"){
        assert(mazeColor.fgIntColor == 16562690)
    }

    test("Maze Color returns solutionIntColor"){
        assert(mazeColor.solutionIntColor == 16562691)
    }

}
