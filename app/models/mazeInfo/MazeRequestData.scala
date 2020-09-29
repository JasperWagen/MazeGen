package models.mazeInfo

case class MazeRequestData(
    mazeDimensions: MazeDimensions = MazeDimensions(70, 40),
    bgHexColor: String = "#4b4b4b",
    fgHexColor: String = "#c8c8c8",
    requiresSolution: Boolean = false){

    val bgIntColor: Int = Integer.parseInt(bgHexColor.substring(1), 16)
    val fgIntColor: Int = Integer.parseInt(fgHexColor.substring(1), 16)
    val solutionColor: Int = 16711680
    val scale: Int = 20
    val solutionSquareSize: Float = 100F
    val pathSquareSize: Float = 3.5F
}

