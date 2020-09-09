package services.mazeGen

object testPopulateMaze{
    def main(args: Array[String]): Unit = {
        val test = new PopulateMaze
        val canvas = test.canvas(40, 25)

        val popCanvas = test.populate(canvas)

        for(i <- popCanvas.indices){
            for(j <- popCanvas(0).indices){
                if(popCanvas(i)(j) == 0){
                    print("# ")
                } else {
                    print("  ")
                }

            }
            println("")
        }
    }
}