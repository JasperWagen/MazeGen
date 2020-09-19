package services.mazeSolver

import scala.collection.immutable.List
import services.mazeGen.MazeGen
import models.mazeInfo.MazeDimensions

import scala.util.control.Breaks.breakable
import scala.annotation
import scala.annotation.tailrec


class DepthFirstSearch extends CheckNodes {
    @tailrec
    final def nodeIteration(position: (Int, Int), maze: Array[Array[Byte]], visitedNodes: Array[Array[Byte]], searchPath: List[(Int, Int)]):
        List[(Int, Int)] = {

        val (j, i) = position

        if(checkPositionIsExit(maze, i)){
            val updatedSearchPath = position :: searchPath
            val finalSearchPath = updatedSearchPath.reverse
            return finalSearchPath
        }

        val updatedVisitedNodes: Array[Array[Byte]] = visitedNodes.updated(j, visitedNodes(j).updated(i, 1:Byte))

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

    def search(maze: Array[Array[Byte]]): List[(Int, Int)]={
        if(checkNullCase(maze)){
            return List()
        }
        val mazeGen = new MazeGen
        val mazeDimensions = MazeDimensions(maze(0).length, maze.length)
        val visitedNodes = mazeGen.canvas(mazeDimensions)
        val searchPath: List[(Int, Int)] = List()

        val position = findEntrance(maze)

        nodeIteration(position, maze, visitedNodes, searchPath)

    }

    def mapSearchPathToArr(searchPath: List[(Int, Int)], mazeDimensions: MazeDimensions): Array[Array[Byte]] = {
        val mazeGen = new MazeGen
        val searchArrInit = mazeGen.canvas(mazeDimensions)

        val searchArr = searchPathRec(searchPath, searchArrInit)

        searchArr
    }

    @tailrec
    final def searchPathRec(searchPath: List[(Int, Int)], searchArr: Array[Array[Byte]]): Array[Array[Byte]] = {
        if (searchPath.isEmpty){
            return searchArr
        }
        val(j, i) = searchPath.head
        val updatedSearchArr = searchArr.updated(j, searchArr(j).updated(i, 1:Byte))

        val updatedSearchPath = searchPath.tail
        searchPathRec(updatedSearchPath, updatedSearchArr)
    }

    private def checkPositionIsExit(maze: Array[Array[Byte]], i: Int): Boolean = {
        i == maze(0).length - 1
    }

    private def checkNullCase(maze: Array[Array[Byte]]): Boolean = {
        maze(0).length == 0
    }
}
