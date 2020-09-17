package services.mazeGen

import scala.annotation.tailrec
import scala.util.Random.nextInt

import models.mazeInfo.MazeDimensions


class MazeGen extends {
    //Contains the methods required to, given height and width, create a Maze as a 2D binary array object.
    //Where 0 represents the walls of the maze and 1 represents the paths
    def canvas(mazeDimensions: MazeDimensions): Array[Array[Int]] ={
        val width = mazeDimensions.width
        val height = mazeDimensions.height

        val mazeMatrix = Array.ofDim[Int](height,width)
        for(j <- mazeMatrix.indices){
            for(i <- mazeMatrix(0).indices){
                mazeMatrix(j)(i) = 0
            }
        }
        mazeMatrix
    }

    def checkForPathProximity(direction: String, position: (Int, Int), mazeCanvas: Array[Array[Int]]): Option[String] = {
        val (j, i) = position
        val jPathProximityRange = Map("north" -> (-2 to -1), "east" -> (-1 to 1), "south" -> (1 to 2), "west" -> (-1 to 1))
        val iPathProximityRange = Map("north" -> (-1 to 1), "east" -> (1 to 2), "south" -> (-1 to 1), "west" -> (-2 to -1))
        try {
            //TODO: Try remove if statement
            for (jCheckIndex <- jPathProximityRange(direction); iCheckIndex <- iPathProximityRange(direction)) {
                if (mazeCanvas(j + jCheckIndex)(i + iCheckIndex) == 1) {
                    return None
                }
            }
        } catch {
            case e: ArrayIndexOutOfBoundsException => return None
        }
        Some(direction)
    }

    def moveOptions(position: (Int, Int), mazeCanvas: Array[Array[Int]]): List[String] ={
        val directionSet = List("north", "east", "south", "west")
        val moveOptionSet = directionSet.flatMap(direction => checkForPathProximity(direction, position, mazeCanvas))
        moveOptionSet
    }

    @tailrec
    final def pathCreator(position: (Int, Int), mazeCanvas: Array[Array[Int]], traceback: List[(Int, Int)]): (Array[Array[Int]], List[(Int, Int)])= {
        val (j, i) = position

        val availableDirections = moveOptions(position, mazeCanvas)

        if (availableDirections.isEmpty) {
            return (mazeCanvas, traceback.take(traceback.length - 1))
        }

        val updatedTraceback = updateTraceback(traceback, position, availableDirections)

        val direction = availableDirections(nextInt(availableDirections.size))

        val directionUpdate = Map("north" -> (j -1, i), "east" -> (j, i + 1), "south" -> (j + 1, i), "west" -> (j, i -1))

        val updatedPosition = directionUpdate(direction)
        val (jUpdate, iUpdate) = updatedPosition

        val updatedMazeCanvas = mazeCanvas.updated(jUpdate, mazeCanvas(jUpdate).updated(iUpdate, 1))
        pathCreator(updatedPosition, updatedMazeCanvas, updatedTraceback)
    }

    private def updateTraceback(traceback: List[(Int, Int)], position: (Int, Int), availableDirections: List[String]): List[(Int, Int)]= {
        if (availableDirections.length > 1) {
            val updatedTraceback =  position :: traceback
            return updatedTraceback
        }
        val updatedTraceback = traceback
        updatedTraceback
    }

    @tailrec
    final def addExit(mazeCanvas: Array[Array[Int]]): Array[Array[Int]] = {
        val exitLoc = nextInt(mazeCanvas.length-2)+1
        if(mazeCanvas(exitLoc)(mazeCanvas(0).length-2)== 0){
            addExit(mazeCanvas)
        } else {
            mazeCanvas(exitLoc)(mazeCanvas(0).length - 1) = 1
            mazeCanvas
        }
    }

    def findRandomStartLocOnLeftSide(mazeCanvas: Array[Array[Int]]): (Int, Int) = {
        val height = mazeCanvas.length
        val offsetTop = 2
        val offsetBottom = 1
        val startLocationJ = nextInt(height-offsetTop)+offsetBottom
        val startLocationI = 0
        (startLocationJ, startLocationI)
    }

    def addEntrance(mazeCanvas: Array[Array[Int]], startLocation: (Int, Int)): Array[Array[Int]] = {

        val (startLocationJ, startLocationI) = startLocation
        val mazeWithEntrance = mazeCanvas.updated(startLocationJ, mazeCanvas(startLocationJ).updated(startLocationI, 1))

        mazeWithEntrance
    }


}

