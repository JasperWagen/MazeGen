package services.mazeGen

import scala.collection.mutable.ListBuffer
import scala.util.Random.nextInt

class PopulateMaze extends MazeGen {
    def populate(mazeCanvas: Array[Array[Int]]): Array[Array[Int]] = {
        // TODO: magic numbers

        val startLocation = findRandomStartLocOnLeftSide(mazeCanvas)
        val mazeWithEntrance= addEntrance(mazeCanvas, startLocation)

        val traceback = ListBuffer(startLocation)
        var updatedCanvas = mazeWithEntrance

        var x = 0
        while(x < traceback.size){
            val (j, i) = traceback(x)
            val (recursiveCanvas, tracebackUpdate) = pathCreator(j, i, updatedCanvas)
            traceback.addAll(tracebackUpdate)
            updatedCanvas = recursiveCanvas
            x += 1
        }
        updatedCanvas = addExit(updatedCanvas)
        updatedCanvas
    }
}


