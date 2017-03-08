package core.responses

import core.cliphandler.SoundLib
import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  */
class LibResponse(path:String) extends Response{
  val lib: Option[SoundLib] = Atmosphere.soundLibs.get(path)

  override def html: String = {
    Atmosphere.generateHTML(path, lib.get)
  }
}
