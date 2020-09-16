package services.mazeGen

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

    def checkProximity(direction: Int, j: Int, i: Int, mazeCanvas: Array[Array[Int]]): Boolean = {
        try {
            //TODO: collapse i, j down into type (point)
            //TODO: direction enumeration
            //TODO: rename
            if (direction == 0) {
                for (a <- -1 to 1; b <- -2 to -1) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return true
                    }
                }
            } else if (direction == 1) {
                for (a <- 1 to 2; b <- -1 to 1) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return true
                    }
                }
            } else if (direction == 2) {
                for (a <- -1 to 1; b <- 1 to 2) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return true
                    }
                }
            } else if (direction == 3) {
                for (a <- -2 to -1; b <- -1 to 1) {
                    if (mazeCanvas(j + b)(i + a) == 1) {

                        return true
                    }
                }
            }
        } catch {
            case e: ArrayIndexOutOfBoundsException => return true
        }
        false
    }

    def moveOptions(j: Int, i: Int, mazeCanvas: Array[Array[Int]]): ListBuffer[Int] ={
        var optionSet = ListBuffer[Int]()
        if(!checkProximity(0, j, i, mazeCanvas)){
            optionSet += 0
        }
        if(!checkProximity(1, j, i, mazeCanvas)){
            optionSet += 1
        }
        if(!checkProximity(2, j, i, mazeCanvas)){
            optionSet += 2
        }
        if(!checkProximity(3, j, i, mazeCanvas)){
            optionSet += 3
        }
        optionSet
    }

    def pathCreator(jInput: Int, iInput: Int, mazeCanvas: Array[Array[Int]]): (Array[Array[Int]], ListBuffer[(Int, Int)]) = {
        var i = iInput; var j = jInput
        var traceBack = new ListBuffer[(Int, Int)]

        breakable {
            while (true) {
                val avalibleDirections = moveOptions(j, i, mazeCanvas)

                if (avalibleDirections.isEmpty) {
                    break()
                }

                if (avalibleDirections.size > 1){
                    (j, i) +=: traceBack
                }

                val direction = avalibleDirections(nextInt(avalibleDirections.size))

                if (direction == 0) {
                    mazeCanvas(j - 1)(i) = 1
                    j -= 1
                } else if (direction == 1) {
                    mazeCanvas(j)(i + 1) = 1
                    i += 1
                } else if (direction == 2) {
                    mazeCanvas(j + 1)(i) = 1
                    j += 1
                } else if (direction == 3) {
                    mazeCanvas(j)(i - 1) = 1
                    i -= 1
                }
            }
        }
        (mazeCanvas, traceBack)
    }

    def addExit(mazeCanvas: Array[Array[Int]]): Array[Array[Int]] = {
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

