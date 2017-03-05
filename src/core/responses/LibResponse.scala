package core.responses

/**
  * Created by julian on 05-Mar-17.
  */
class LibResponse(path:String) extends Response{
  override val html: String = {
    val indexSource = io.Source.fromFile("tmp/" + path + ".html")
    try indexSource.mkString finally indexSource.close()
  }
}
