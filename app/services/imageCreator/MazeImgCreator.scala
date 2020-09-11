package services.imageCreator

import java.io.File

import javax.imageio.ImageIO

class MazeImgCreator extends ArrayToImage {
    //child class responsible for implementing the array to image methods for the case of the maze generation website
    def createMazeImg(filePath: String, mazeArr: Array[Array[Int]], bgColor: Int, pathColor: Int): Unit ={
        val imgMkr = new ArrayToImage
        val scale = 20

        var img = imgMkr.createImg(mazeArr, bgColor, scale)
        img = imgMkr.mapArrayToImg(mazeArr, img, pathColor, scale)

        ImageIO.write(img, "png", new File(filePath))
    }
}
