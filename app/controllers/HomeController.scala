package controllers

import java.io.File

import java.nio.file
import play.libs.Files._
import javax.imageio.ImageIO
import javax.inject._
import play.api.mvc._
import services._
import play.api.mvc.ControllerComponents
import services.imageCreator.ArrayToImage
import services.mazeSolver.DepthFirstSearch
import models.mazeInfo.ModifiedMazeData
import models.mazeInfo.MazeDimensions
import play.api.libs.Files

case class MazeForm(height: Int, width: Int, bgColor: String, fgColor: String, solved: Boolean)

//TEST

@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents) extends AbstractController(controllerComponents)
  with play.api.i18n.I18nSupport {
    import play.api.data.Form
    import play.api.data.Forms._

    val mazeForm = Form(
    mapping(
      "Height" -> number,
      "Width" -> number,
      "BGColor" -> text,
      "FGColor" -> text,
      "Solved" -> boolean
    )(MazeForm.apply)(MazeForm.unapply)
  )

  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  def serveImg(imgPath: String) = Action {
    Ok.sendFile(new java.io.File("/"+imgPath))
  }

  def newMaze(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    mazeForm.bindFromRequest().fold(
      formWithErrors => {
        BadRequest("bad request")
      },
      mazeData => {
        //TODO: pullout to function
        val width = mazeData.width
        val height = mazeData.height
        val bgHexColor = mazeData.bgColor
        val fgHexColor = mazeData.fgColor

        val mazeDimensions = MazeDimensions(width, height)

        val bgIntColor = Integer.parseInt(bgHexColor.substring(1), 16)
        val fgIntColor = Integer.parseInt(fgHexColor.substring(1), 16)

        val maze = new mazeGen.PopulateMaze
        val canvas = maze.canvas(mazeDimensions)
        val popCanvas = maze.populate(canvas)

        val imgCreator = new imageCreator.MazeImgCreator
        val arrayToImage = new ArrayToImage
        val bgColor = bgIntColor
        val pathColor = fgIntColor
        val mazeImg = imgCreator.createMazeImg(popCanvas, bgColor, pathColor)

        val modifiedMazeData = ModifiedMazeData(mazeDimensions, bgHexColor, fgHexColor, mazeData.solved)

        if(mazeData.solved) {
          val dfSearch = new DepthFirstSearch
          val searchPath = dfSearch.search(popCanvas)
          val searchArr = dfSearch.mapSearchPathToArr(searchPath, mazeDimensions)
          val solvedMazeImg = arrayToImage.mapArrayToImg(searchArr, mazeImg, 16711680, 20, 100F)
          val tempMazeImg = File.createTempFile("mazeImg", ".png")

          ImageIO.write(mazeImg, "png", tempMazeImg)
          Ok(views.html.index(mazeForm, modifiedMazeData, tempMazeImg.getAbsolutePath))
        }
        val tempMazeImg = File.createTempFile("mazeImg", ".png")
        ImageIO.write(mazeImg, "png", tempMazeImg)

        Ok(views.html.index(mazeForm, modifiedMazeData, tempMazeImg.getAbsolutePath))
      }
    )

  }

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val maze = new mazeGen.PopulateMaze

    val mazeDimensions = MazeDimensions(70, 40)

    val bgIntColor = 4934475
    val fgIntColor = 13158600

    val bgHexColor = "#" + Integer.toHexString(bgIntColor)
    val fgHexColor = "#" + Integer.toHexString(fgIntColor)

    val solved = false

    val canvas = maze.canvas(mazeDimensions)
    val popCanvas = maze.populate(canvas)

    val imgCreator = new imageCreator.MazeImgCreator
    val arrayToImage = new ArrayToImage
    val mazeImg = imgCreator.createMazeImg(popCanvas, bgIntColor, fgIntColor)

    //wrap data
    val modifiedMazeData = ModifiedMazeData(mazeDimensions, bgHexColor, fgHexColor, solved)

    val tempMazeImg = File.createTempFile("mazeImg", ".png")
    ImageIO.write(mazeImg, "png", tempMazeImg)

    Ok(views.html.index(mazeForm, modifiedMazeData, tempMazeImg.getAbsolutePath))
  }
}
