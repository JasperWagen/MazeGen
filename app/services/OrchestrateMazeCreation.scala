package services

import java.awt.image.BufferedImage

import models.mazeInfo.MazeDataHolder
import services.mazeSolver.DepthFirstSearch

class OrchestrateMazeCreation {
    def create(mazeDataHolder: MazeDataHolder): BufferedImage = {

        val maze = new mazeGen.PopulateMaze
        val canvas = maze.canvas(mazeDataHolder.mazeDimensions)
        val popCanvas = maze.populate(canvas)

        val imgCreator = new imageCreator.MazeImgCreator

        val mazeImg = imgCreator.createMazeImg(popCanvas, mazeDataHolder.bgIntColor, mazeDataHolder.fgIntColor)

        if(mazeDataHolder.solved) {
            val dfSearch = new DepthFirstSearch
            val searchPath = dfSearch.search(popCanvas)
            val searchArr = dfSearch.mapSearchPathToArr(searchPath, mazeDataHolder.mazeDimensions)
            val solvedMazeImg = imgCreator.mapArrayToImg(
                searchArr, mazeImg, mazeDataHolder.pathColor, mazeDataHolder.scale, mazeDataHolder.squareSize)

            return solvedMazeImg
        }
    mazeImg
    }
}

