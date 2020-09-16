package services.mazeGen

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.Random.nextInt
import scala.util.control.Breaks.{break, breakable}


class MazeGen{
    //Contains the methods required to, given height and width, create a Maze as a 2D binary array object.
    //Where 0 represents the walls of the maze and 1 represents the paths
    def canvas(width: Int, height: Int): Array[Array[Int]] ={
        val mazeMatrix = Array.ofDim[Int](height,width)
        for(i <- mazeMatrix.indices){
            for(j <- mazeMatrix(0).indices){
                mazeMatrix(i)(j) = 0
            }
        }
        mazeMatrix
    }

    def checkProximity(direction: Int, j: Int, i: Int, mazeCanvas: Array[Array[Int]]): Option[Int] = {
        try {
            //TODO: collapse i, j down into type (point)
            //TODO: direction enumeration
            //TODO: rename
            if (direction == 0) {
                for (a <- -1 to 1; b <- -2 to -1) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return None
                    }
                }
            } else if (direction == 1) {
                for (a <- 1 to 2; b <- -1 to 1) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return None
                    }
                }
            } else if (direction == 2) {
                for (a <- -1 to 1; b <- 1 to 2) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return None
                    }
                }
            } else if (direction == 3) {
                for (a <- -2 to -1; b <- -1 to 1) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return None
                    }
                }
            }
        } catch {
            case e: ArrayIndexOutOfBoundsException => return None
        }
        Some(direction)
    }

    def moveOptions(j: Int, i: Int, mazeCanvas: Array[Array[Int]]): List[Int] ={
        val directionSet = List(0, 1, 2, 3)
        val moveOptionSet = directionSet.flatMap(direction => checkProximity(direction, j, i, mazeCanvas))
        moveOptionSet
    }

    @tailrec
    final def pathCreator(jInput: Int, iInput: Int, mazeCanvas: Array[Array[Int]], traceback: List[(Int, Int)]): (Array[Array[Int]], List[(Int, Int)])= {
        val i = iInput; val j = jInput

        val availableDirections = moveOptions(j, i, mazeCanvas)

        if (availableDirections.isEmpty) {
            return (mazeCanvas, traceback.take(traceback.length - 1))
        }

        val updatedTraceback = updateTraceback(traceback, j, i, availableDirections)

        val direction = availableDirections(nextInt(availableDirections.size))

        if (direction == 0) {
            mazeCanvas(j - 1)(i) = 1
            val jUpdate = j - 1
            val iUpdate = i
            pathCreator(jUpdate, iUpdate, mazeCanvas, updatedTraceback)
        } else if (direction == 1) {
            mazeCanvas(j)(i + 1) = 1
            val jUpdate = j
            val iUpdate = i + 1
            pathCreator(jUpdate, iUpdate, mazeCanvas, updatedTraceback)
        } else if (direction == 2) {
            mazeCanvas(j + 1)(i) = 1
            val jUpdate = j + 1
            val iUpdate = i
            pathCreator(jUpdate, iUpdate, mazeCanvas, updatedTraceback)
        } else {
            mazeCanvas(j)(i - 1) = 1
            val jUpdate = j
            val iUpdate = i - 1
            pathCreator(jUpdate, iUpdate, mazeCanvas, updatedTraceback)
        }
    }

    private def updateTraceback(traceback: List[(Int, Int)], j: Int, i: Int, availableDirections: List[Int]): List[(Int, Int)]= {
        if (availableDirections.length > 1) {
            val updatedTraceback =  (j, i) :: traceback
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

