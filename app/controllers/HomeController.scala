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
import models.mazeInfo.MazeDataHolder
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

        val mazeDimensions = MazeDimensions(mazeData.width, mazeData.height)

        val mazeDataHolder = MazeDataHolder(mazeDimensions, mazeData.bgColor, mazeData.fgColor, mazeData.solved)

        val maze = new mazeGen.PopulateMaze
        val canvas = maze.canvas(mazeDimensions)
        val popCanvas = maze.populate(canvas)

        val imgCreator = new imageCreator.MazeImgCreator
        val arrayToImage = new ArrayToImage

        val mazeImg = imgCreator.createMazeImg(popCanvas, mazeDataHolder.bgIntColor, mazeDataHolder.fgIntColor)

        if(mazeData.solved) {
          val dfSearch = new DepthFirstSearch
          val searchPath = dfSearch.search(popCanvas)
          val searchArr = dfSearch.mapSearchPathToArr(searchPath, mazeDimensions)
          val solvedMazeImg = arrayToImage.mapArrayToImg(searchArr, mazeImg, 16711680, 20, 100F)
          val tempMazeImg = File.createTempFile("mazeImg", ".png")

          ImageIO.write(solvedMazeImg, "png", tempMazeImg)
          Ok(views.html.index(mazeForm, mazeDataHolder, tempMazeImg.getAbsolutePath))
        }
        val tempMazeImg = File.createTempFile("mazeImg", ".png")
        ImageIO.write(mazeImg, "png", tempMazeImg)

        Ok(views.html.index(mazeForm, mazeDataHolder, tempMazeImg.getAbsolutePath))
      }
    )

  }

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val maze = new mazeGen.PopulateMaze

    val mazeDataHolder = MazeDataHolder()

    val canvas = maze.canvas(mazeDataHolder.mazeDimensions)
    val popCanvas = maze.populate(canvas)

    val imgCreator = new imageCreator.MazeImgCreator
    val mazeImg = imgCreator.createMazeImg(popCanvas, mazeDataHolder.bgIntColor, mazeDataHolder.fgIntColor)

    val tempMazeImg = File.createTempFile("mazeImg", ".png")
    ImageIO.write(mazeImg, "png", tempMazeImg)

    Ok(views.html.index(mazeForm, mazeDataHolder, tempMazeImg.getAbsolutePath))
  }
}
