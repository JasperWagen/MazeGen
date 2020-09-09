package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services._
import play.api.data._
import play.api.data.Forms._

import play.api.i18n.Lang
import play.api.i18n.Langs
import play.api.mvc.BaseController
import play.api.mvc.ControllerComponents

case class MazeForm(height: Int, width: Int)


@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents) extends AbstractController(controllerComponents)
  with play.api.i18n.I18nSupport {
    import play.api.data.Form
    import play.api.data.Forms._

    val mazeForm = Form(
    mapping(
      "height" -> number,
      "width" -> number
    )(MazeForm.apply)(MazeForm.unapply)
  )

  def newMaze(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    //val mazeData = mazeForm.bindFromRequest()
    val maze = new mazeGen.PopulateMaze
    val canvas = maze.canvas(10, 40)
    val popCanvas = maze.populate(canvas)

    val imgCreator = new imageCreator.MazeImgCreator
    val bgColor = 4934475
    val pathColor = 13158600
    imgCreator.createMazeImg("public/images/mazeBucket/TEST.png", popCanvas, bgColor, pathColor)
    Ok(views.html.index(mazeForm))
  }

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val maze = new mazeGen.PopulateMaze
    val canvas = maze.canvas(70, 40)
    val popCanvas = maze.populate(canvas)

    val imgCreator = new imageCreator.MazeImgCreator
    val bgColor = 4934475
    val pathColor = 13158600
    imgCreator.createMazeImg("public/images/mazeBucket/TEST.png", popCanvas, bgColor, pathColor)
    Ok(views.html.index(mazeForm))
  }
}
