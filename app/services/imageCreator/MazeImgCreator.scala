package services.imageCreator

import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO
import models.mazeInfo.MazeRequestData

class MazeImgCreator extends ArrayToImage {
    def createMazeImg(mazeArr: Array[Array[Byte]], mazeRequestObject: MazeRequestData): BufferedImage ={
        val imgMkr = new ArrayToImage

        var img = imgMkr.imgInit(mazeArr, mazeRequestObject)
        img = imgMkr.mapArrayToImg(
            mazeArr, img, mazeRequestObject.scale, mazeRequestObject.fgIntColor, mazeRequestObject.pathSquareSize)

        img
    }
}
