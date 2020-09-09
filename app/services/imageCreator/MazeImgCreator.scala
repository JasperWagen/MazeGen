package services.imageCreator

import java.io.File

import javax.imageio.ImageIO

class MazeImgCreator extends ArrayToImage {
    def createMazeImg(filePath: String, mazeArr: Array[Array[Int]], bgColor: Int, pathColor: Int): Unit ={
        val imgMkr = new ArrayToImage

        var img = imgMkr.createImg(mazeArr, bgColor)
        img = imgMkr.mapArrayToImg(mazeArr, img, pathColor)

        ImageIO.write(img, "png", new File(filePath))
    }
}
