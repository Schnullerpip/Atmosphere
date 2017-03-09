package core.responses

import core.cliphandler.SoundLib
import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  * The response for opening a specific Lib
  */
case class LibResponse(path:String) extends Response{
  val lib: Option[SoundLib] = Atmosphere.soundLibs.get(path)

  override def html: String = {
    if(lib.nonEmpty)
      Atmosphere.generateHTML(path, lib.get)
    else
      NotFoundResponse gen()
  }
}
