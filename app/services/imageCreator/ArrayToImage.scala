package services.imageCreator

import java.awt.image.BufferedImage

class ArrayToImage {
    //Contains methods for creating a buffered image object based on a 2D array (createImg) and then mapping the array
    // values to specified colors in the image (mapArrayToImg)

    def createImg(mazeCanvas: Array[Array[Byte]], bgColor: Int, scale: Int): BufferedImage = {
        val height = mazeCanvas.length*scale
        val width = mazeCanvas(0).length*scale
        val img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        //Set BG Color
        for(i <- 0 until img.getWidth(); j <- 0 until img.getHeight()){
            img.setRGB(i, j, bgColor)
        }


        img
    }

    def mapArrayToImg(mazeCanvas: Array[Array[Byte]], img: BufferedImage, pathColor: Int, scale: Int, squareSize: Float): BufferedImage = {
        //Upper and lower bounds used here to make the path width greater than that of the walls
        val lowBound = -(scale/squareSize).toInt
        val upBound = scale + (scale/squareSize).toInt

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
                        for (iImg <- 0 until scale; jImg <- lowBound to upBound) {
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




