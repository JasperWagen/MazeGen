package models.mazeInfo

case class MazeDataHolder(
    mazeDimensions: MazeDimensions = MazeDimensions(70, 40),
    bgHexColor: String = "#4b4b4b",
    fgHexColor: String = "#c8c8c8",
    solved: Boolean = false){

    val bgIntColor: Int = Integer.parseInt(bgHexColor.substring(1), 16)
    val fgIntColor: Int = Integer.parseInt(fgHexColor.substring(1), 16)
}

