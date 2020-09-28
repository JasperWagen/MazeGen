package services.mazeGen

import scala.annotation.tailrec

class PopulateMaze extends MazeGen {
    def populate(mazeCanvas: Array[Array[Byte]]): Array[Array[Byte]] = {

        val startLocation = findRandomStartLocOnLeftSide(mazeCanvas)
        val mazeWithEntrance= addEntrance(mazeCanvas, startLocation)

        val traceback = List(startLocation)
        val recursionMaze = mazeWithEntrance

        val iterationCount = 0

        val populatedMaze = createPathRecursion(iterationCount, traceback, recursionMaze)

        val finalMaze = addExit(populatedMaze)
        finalMaze
    }

    @tailrec
    final def createPathRecursion(iterationCount: Int, traceback: List[(Int, Int)], recursionMaze: Array[Array[Byte]]): Array[Array[Byte]] = {
        if(traceback.isEmpty){
            return recursionMaze
        }

        val position = traceback.last
        val (updatedRecursionMaze, updatedTraceback) = pathCreator(position, recursionMaze, traceback)

        val updatedIterationCount = iterationCount + 1
        createPathRecursion(updatedIterationCount, updatedTraceback, updatedRecursionMaze)
    }
}


