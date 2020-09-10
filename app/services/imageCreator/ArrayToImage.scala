package services.imageCreator

import java.awt.image.BufferedImage

class ArrayToImage {
    def createImg(mazeCanvas: Array[Array[Int]], bgColor: Int): BufferedImage = {
        val scale = 20
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
        val scale = 20
        val lowBound = -(scale/3.5).toInt
        val upBound = scale + (scale/3.5).toInt

        for(iMaze <- mazeCanvas(0).indices; jMaze <- mazeCanvas.indices){
            if(mazeCanvas(jMaze)(iMaze) == 1){
                try {
                    for (iImg <- lowBound to upBound; jImg <- lowBound to upBound) {
                        val iPos = (iMaze * scale) + iImg
                        val jPos = (jMaze * scale) + jImg
                        img.setRGB(iPos, jPos, pathColor)
                    }
                } catch {
                    case e: ArrayIndexOutOfBoundsException => {
                        for (iImg <- 0 to scale-1; jImg <- lowBound to upBound) {
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




