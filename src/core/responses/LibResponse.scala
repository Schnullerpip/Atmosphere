package core.responses

import core.cliphandler.SoundLib
import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  */
class LibResponse(path:String) extends Response{
  val lib: Option[SoundLib] = Atmosphere.soundLibs.get(path)

  override val html: String = {
    //val indexSource = io.Source.fromFile("tmp/" + path + ".html")
    //try indexSource.mkString finally indexSource.close()

    Atmosphere.generateHTML(path, lib.get)
  }
}
