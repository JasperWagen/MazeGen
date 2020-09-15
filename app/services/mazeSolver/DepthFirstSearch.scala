package services.mazeSolver

import scala.collection.immutable.List
import services.mazeGen.MazeGen
import scala.util.control.Breaks.breakable
import scala.annotation


class DepthFirstSearch extends CheckNodes {
    def nodeIteration(position: (Int, Int), maze: Array[Array[Int]], visitedNodes: Array[Array[Int]], searchPath: List[(Int, Int)]):
        List[(Int, Int)] = {

        val (j, i) = position
        //checks if at exit
        if(i == maze(0).length-1){
            val updatedSearchPath = position :: searchPath
            val finalSearchPath = updatedSearchPath.reverse

            return finalSearchPath
        }

        val updatedVisitedNodes: Array[Array[Int]] = visitedNodes.updated(j, visitedNodes(j).updated(i, 1))

        val availableNodes = checkAvailableNodes(maze, visitedNodes, position)

        if(availableNodes.isEmpty){
            //pop stack
            val updatedSearchPath = searchPath.tail
            val updatedPosition = updatedSearchPath.head

            nodeIteration(updatedPosition, maze, updatedVisitedNodes, updatedSearchPath)
        }else{
            //push stack
            val updatedSearchPath = position :: searchPath
            val updatedPosition = availableNodes.head

            nodeIteration(updatedPosition, maze, updatedVisitedNodes, updatedSearchPath)
        }


    }

    def search(maze: Array[Array[Int]]): List[(Int, Int)]={
        if(maze(0).length == 0){
            return List()
        }
        val mazeGen = new MazeGen
        val visitedNodes = mazeGen.canvas(maze(0).length, maze.length)
        val searchPath: List[(Int, Int)] = List()

        val position = findEntrance(maze)

        //@annotation.tailrec
        nodeIteration(position, maze, visitedNodes, searchPath)

    }
}
