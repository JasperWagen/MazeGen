package services.mazeSolver
import scala.collection.immutable.List

class CheckNodes {
    def findEntrance(maze: Array[Array[Byte]]): (Int, Int) = {
        for(j <- maze.indices){
            if(maze(j)(0) == 1) {
                return (j, 0)
            }
        }
        throw new Exception("Entrance not found; aborting depth first search")
    }

    def checkPossibleNodes(maze: Array[Array[Byte]], position: (Int, Int), delta: (Int, Int)): Option[(Int, Int)] ={
        val (j, i) = position
        val (deltaJ, deltaI) = delta
        try {
            if(maze(j+deltaJ)(i+deltaI) == 1) {
                return Some(j + deltaJ, i + deltaI)
            }
        } catch {
            case e: ArrayIndexOutOfBoundsException => return None
        }
        None

    }

    def checkVisitedNodes(visitedNodes: Array[Array[Byte]], position: (Int, Int)): Option[(Int, Int)] = {
        val (j, i) = position
        try {
            if (visitedNodes(j)(i) == 0) {
                return Some(j, i)
            }
        } catch {
            case e: ArrayIndexOutOfBoundsException => return None
        }
        None
    }

    def checkAvailableNodes(maze: Array[Array[Byte]], visitedNodes: Array[Array[Byte]], position: (Int, Int)): List[(Int, Int)] = {
        val deltaList = List((-1, 0), (0, 1), (1, 0), (0, -1))
        val possibleNodes = deltaList.flatMap(delta => checkPossibleNodes(maze, position, delta))
        val availableNodes = possibleNodes.flatMap(possibleNode => checkVisitedNodes(visitedNodes, possibleNode))
        availableNodes
    }



}
