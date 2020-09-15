package services.mazeSolver
import services.mazeGen._


object MazeSolverTest {
    def main(args: Array[String]): Unit = {
        val mazeGen = new MazeGen
        val populate = new PopulateMaze
        var testMaze = mazeGen.canvas(20, 10)
        testMaze = populate.populate(testMaze)

        for(i <- testMaze.indices){
            for(j <- testMaze(0).indices) {
                print(testMaze(i)(j) + " ")
            }
            println()
        }


    }
}
