package models.mazeInfo

class MazeColor(val bgHexColor: String, val fgHexColor: String, val solutionHexColor: String) {
    def convertToIntColor(hexColor: String): Int = {
        val intColor = Integer.parseInt(hexColor.substring(1), 16)
        intColor
    }

    val bgIntColor: Int = convertToIntColor(bgHexColor)
    val fgIntColor: Int = convertToIntColor(fgHexColor)
    val solutionIntColor: Int = convertToIntColor(solutionHexColor)
}
