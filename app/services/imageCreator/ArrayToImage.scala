package services.imageCreator

import java.awt.image.BufferedImage

class ArrayToImage {
    def createImg(mazeCanvas: Array[Array[Int]], bgColor: Int): BufferedImage = {
        val scale = 5
        val height = mazeCanvas.length*scale
        val width = mazeCanvas(0).length*scale
        val img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        //Set BG Color
        for(i <- 0 until img.getWidth(); j <- 0 until img.getHeight()){
            img.setRGB(i, j, bgColor)
        }
        img
    }

    def mapArrayToImg(mazeCanvas: Array[Array[Int]], img: BufferedImage, pathColor: Int): BufferedImage = {
        val scale = 5

        for(iMaze <- mazeCanvas(0).indices; jMaze <- mazeCanvas.indices){
            if(mazeCanvas(jMaze)(iMaze) == 1){
                try {
                    for (iImg <- -1 to 5; jImg <- -1 to 5) {
                        val iPos = (iMaze * scale) + iImg
                        val jPos = (jMaze * scale) + jImg
                        img.setRGB(iPos, jPos, pathColor)
                    }
                } catch {
                    case e: ArrayIndexOutOfBoundsException => {
                        for (iImg <- 0 to 4; jImg <- -1 to 5) {
                            val iPos = (iMaze * scale) + iImg
                            val jPos = (jMaze * scale) + jImg
                            img.setRGB(iPos, jPos, pathColor)
                        }
                    }
                }

            }
        }
        img
    }
}




