package services.imageCreator

import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

class MazeImgCreator extends ArrayToImage {
    //child class responsible for implementing the array to image methods for the case of the maze generation website
    def createMazeImg(mazeArr: Array[Array[Int]], bgColor: Int, pathColor: Int): BufferedImage ={
        val imgMkr = new ArrayToImage
        val scale = 20
        val squareSize = 3.5F

        var img = imgMkr.createImg(mazeArr, bgColor, scale)
        img = imgMkr.mapArrayToImg(mazeArr, img, pathColor, scale, squareSize)

        img
    }
}
