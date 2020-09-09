package services.imageCreator

object imgTest {
    def main(args: Array[String]): Unit = {
        val test = new PopulateMaze
        val canvas = test.canvas(100, 75)

        val popCanvas = test.populate(canvas)

        val imgCreator = new MazeImgCreator
        imgCreator.createMazeImg("test.png", popCanvas)
    }
}
