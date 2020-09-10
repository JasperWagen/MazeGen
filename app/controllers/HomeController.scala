package controllers

import javax.inject._
import play.api.mvc._
import services._

import play.api.mvc.ControllerComponents

case class MazeForm(height: Int, width: Int, bgColor: String, fgColor: String)


@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents) extends AbstractController(controllerComponents)
  with play.api.i18n.I18nSupport {
    import play.api.data.Form
    import play.api.data.Forms._

    val mazeForm = Form(
    mapping(
      "Width" -> number,
      "Height" -> number,
      "BGColor" -> text,
      "FGColor" -> text
    )(MazeForm.apply)(MazeForm.unapply)
  )

  def newMaze(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    mazeForm.bindFromRequest().fold(
      formWithErrors => {
        //TODO: implement bad request
        BadRequest("bad request")
      },
      mazeData => {
        //gather data
        val width = mazeData.width
        val height = mazeData.height
        val bgHexColor = mazeData.bgColor
        val fgHexColor = mazeData.fgColor

        //convert color format
        val bgIntColor = Integer.parseInt(bgHexColor.substring(1), 16)
        val fgIntColor = Integer.parseInt(fgHexColor.substring(1), 16)

        //generate maze
        val maze = new mazeGen.PopulateMaze
        val canvas = maze.canvas(height, width)
        val popCanvas = maze.populate(canvas)

        val imgCreator = new imageCreator.MazeImgCreator
        val bgColor = bgIntColor
        val pathColor = fgIntColor
        imgCreator.createMazeImg("public/images/mazeBucket/TEST.png", popCanvas, bgColor, pathColor)

        Ok(views.html.index(mazeForm, width, height, bgHexColor, fgHexColor))
      }
    )


  }

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    val maze = new mazeGen.PopulateMaze

    //default values
    val width = 70
    val height = 40
    val bgIntColor = 4934475
    val fgIntColor = 13158600

    val bgHexColor = "#" + Integer.toHexString(bgIntColor)
    val fgHexColor = "#" + Integer.toHexString(fgIntColor)

    val canvas = maze.canvas(width, height)
    val popCanvas = maze.populate(canvas)

    val imgCreator = new imageCreator.MazeImgCreator

    imgCreator.createMazeImg("public/images/mazeBucket/TEST.png", popCanvas, bgIntColor, fgIntColor)
    Ok(views.html.index(mazeForm, width, height, bgHexColor, fgHexColor))
  }
}
