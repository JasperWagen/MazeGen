package services.mazeSolver

import scala.collection.immutable.List
import services.mazeGen.MazeGen
import models.mazeInfo.MazeDimensions

import scala.util.control.Breaks.breakable
import scala.annotation
import scala.annotation.tailrec


class DepthFirstSearch extends CheckNodes {
    @tailrec
    final def nodeIteration(position: (Int, Int), maze: Array[Array[Int]], visitedNodes: Array[Array[Int]], searchPath: List[(Int, Int)]):
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
            val updatedPosition = searchPath.head

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
        val mazeDimensions = MazeDimensions(maze(0).length, maze.length)
        val visitedNodes = mazeGen.canvas(mazeDimensions)
        val searchPath: List[(Int, Int)] = List()

        val position = findEntrance(maze)

        nodeIteration(position, maze, visitedNodes, searchPath)

    }

    def mapSearchPathToArr(searchPath: List[(Int, Int)], maze: Array[Array[Int]], mazeDimensions: MazeDimensions): Array[Array[Int]] = {
        val mazeGen = new MazeGen
        val searchArrInit = mazeGen.canvas(mazeDimensions)

        val searchArr = searchPathRec(searchPath, searchArrInit)

        searchArr
    }

    def searchPathRec(searchPath: List[(Int, Int)], searchArr: Array[Array[Int]]): Array[Array[Int]] = {
        if (searchPath.isEmpty){
            return searchArr
        }
        val(j, i) = searchPath.head
        val updatedSearchArr = searchArr.updated(j, searchArr(j).updated(i, 1))

        val updatedSearchPath = searchPath.tail
        searchPathRec(updatedSearchPath, updatedSearchArr)
    }
}
