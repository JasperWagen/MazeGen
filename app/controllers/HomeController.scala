package controllers

import java.io.File

import javax.imageio.ImageIO
import javax.inject._
import play.api.mvc._
import services._
import play.api.mvc.ControllerComponents
import models.mazeInfo.MazeRequestData
import models.mazeInfo.MazeDimensions

case class MazeForm(height: Int, width: Int, bgColor: String, fgColor: String, solved: Boolean)

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
      mazeFormData => {
        val mazeDimensions = MazeDimensions(mazeFormData.width, mazeFormData.height)
        val mazeRequestObject = MazeRequestData(
          mazeDimensions, mazeFormData.bgColor, mazeFormData.fgColor, mazeFormData.solved)

        val orchestrateMazeCreation = new OrchestrateMazeCreation
        val mazeImg = orchestrateMazeCreation.create(mazeRequestObject)

        val tempMazeImg = File.createTempFile("mazeImg", ".png")
        ImageIO.write(mazeImg, "png", tempMazeImg)

        Ok(views.html.index(mazeForm, mazeRequestObject, tempMazeImg.getAbsolutePath))
      }
    )
  }

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val mazeRequestObject = MazeRequestData()

    val orchestrateMazeCreation = new OrchestrateMazeCreation
    val mazeImg = orchestrateMazeCreation.create(mazeRequestObject)

    val tempMazeImg = File.createTempFile("mazeImg", ".png")
    ImageIO.write(mazeImg, "png", tempMazeImg)

    Ok(views.html.index(mazeForm, mazeRequestObject, tempMazeImg.getAbsolutePath))
  }
}
