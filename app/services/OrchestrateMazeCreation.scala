package services

import java.awt.image.BufferedImage

import models.mazeInfo.MazeRequestData
import services.mazeSolver.DepthFirstSearch

class OrchestrateMazeCreation {
    def create(mazeRequestObject: MazeRequestData): BufferedImage = {

        val maze = new mazeGen.PopulateMaze
        val canvas = maze.canvas(mazeRequestObject.mazeDimensions)
        val populatedCanvas = maze.populate(canvas)

        val imgCreator = new imageCreator.MazeImgCreator

        val mazeImg = imgCreator.createMazeImg(populatedCanvas, mazeRequestObject)

        if(mazeRequestObject.requiresSolution) {
            val depthFirstSearch = new DepthFirstSearch
            val searchPath = depthFirstSearch.search(populatedCanvas)
            val searchArray = depthFirstSearch.mapSearchPathToArray(searchPath, mazeRequestObject.mazeDimensions)
            val solvedMazeImg = imgCreator.mapArrayToImg(
                searchArray, mazeImg, mazeRequestObject.scale, mazeRequestObject.solutionColor,
                mazeRequestObject.solutionSquareSize)

            return solvedMazeImg
        }
        mazeImg
    }
}

