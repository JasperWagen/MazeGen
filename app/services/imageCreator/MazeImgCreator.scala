package services.imageCreator

import java.io.File

import javax.imageio.ImageIO

class MazeImgCreator extends ArrayToImage {
    def createMazeImg(filePath: String, mazeArr: Array[Array[Int]]): Unit ={
        val imgMkr = new ArrayToImage

        var img = imgMkr.createImg(mazeArr, 4934475)
        img = imgMkr.mapArrayToImg(mazeArr, img, 13158600)

        ImageIO.write(img, "png", new File(filePath))
    }
}
