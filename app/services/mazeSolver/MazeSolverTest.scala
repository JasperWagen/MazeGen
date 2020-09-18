package services.mazeSolver
import java.io.File

import javax.imageio.ImageIO
import services.imageCreator.ArrayToImage
import services.{imageCreator, mazeGen}
import services.mazeGen._
import models.mazeInfo.MazeDimensions


object MazeSolverTest {
    def main(args: Array[String]): Unit = {
        val maze = new mazeGen.PopulateMaze

        //default values
        val width = 10
        val height = 10
        val mazeDimensions = MazeDimensions(width, height)

        val bgIntColor = 4934475
        val fgIntColor = 13158600

        val bgHexColor = "#" + Integer.toHexString(bgIntColor)
        val fgHexColor = "#" + Integer.toHexString(fgIntColor)

        val canvas = maze.canvas(mazeDimensions)
        val popCanvas = maze.populate(canvas)

        val imgCreator = new imageCreator.MazeImgCreator
        val arrayToImage = new ArrayToImage
        val mazeImg = imgCreator.createMazeImg(popCanvas, bgIntColor, fgIntColor)

        val dfSearch = new DepthFirstSearch
        for(i <- popCanvas.indices){
            for(j <- popCanvas(0).indices) {
                print(popCanvas(i)(j) + " ")
            }
            println()
        }
        val searchPath = dfSearch.search(popCanvas)
        val searchArr = dfSearch.mapSearchPathToArr(searchPath, mazeDimensions)
        val solvedMazeImg = arrayToImage.mapArrayToImg(searchArr, mazeImg, 16711680, 20, 6F)

        ImageIO.write(solvedMazeImg, "png", new File("public/images/mazeBucket/TEST.png"))
//        for(i <- testMaze.indices){
//            for(j <- testMaze(0).indices) {
//                print(testMaze(i)(j) + " ")
//            }
//            println()
//        }


//        val j = 1
//        val directionUpdate = Map("north" -> (j - 1))
//        val jUpdate = directionUpdate("north")
//        print(jUpdate)
    }


}
