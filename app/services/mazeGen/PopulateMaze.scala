package services.mazeGen

import scala.collection.mutable.ListBuffer
import scala.util.Random.nextInt

class PopulateMaze extends MazeGen {
    def populate(mazeCanvas: Array[Array[Int]]): Array[Array[Int]] = {
        val startLoc = nextInt(mazeCanvas.length-2)+1
        mazeCanvas(startLoc)(0) = 1

        val i = 0
        val j = startLoc

        val traceback = ListBuffer((i, j))
        var updatedCanvas = mazeCanvas

        var x = 0
        while(x < traceback.size){
            val (i, j) = traceback(x)
            val (recCanvas, tracebackUpdate) = pathCreator(i, j, updatedCanvas)
            traceback.addAll(tracebackUpdate)
            updatedCanvas = recCanvas
            x += 1
        }
        updatedCanvas = addExit(updatedCanvas)
        updatedCanvas
    }
}


