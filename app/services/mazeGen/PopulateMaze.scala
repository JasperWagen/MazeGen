package services.mazeGen

import scala.collection.mutable.ListBuffer
import scala.util.Random.nextInt

class PopulateMaze extends MazeGen {
    def populate(mazeCanvas: Array[Array[Int]]): Array[Array[Int]] = {
        // TODO: magic numbers

        val (mazeWithEntrance, (j, i)) = addEntranceOnLeftSide(mazeCanvas)

        val traceback = ListBuffer((i, j))
        var updatedCanvas = mazeWithEntrance

        var x = 0
        while(x < traceback.size){
            val (i, j) = traceback(x)
            val (recursiveCanvas, tracebackUpdate) = pathCreator(i, j, updatedCanvas)
            traceback.addAll(tracebackUpdate)
            updatedCanvas = recursiveCanvas
            x += 1
        }
        updatedCanvas = addExit(updatedCanvas)
        updatedCanvas
    }
}


