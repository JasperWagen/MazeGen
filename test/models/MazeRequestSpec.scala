package models

import org.scalatest.funsuite.AnyFunSuite
import models.MazeRequest
import models.mazeInfo._

class MazeRequestSpec extends AnyFunSuite{
    val height = 10
    val width = 15
    val mazeDimensions = MazeDimensions(width, height)
    val mazeRequest = new MazeRequest(mazeDimensions)


}
