package core.responses

import core.cliphandler.{Sound, SoundLib}
import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  * Lib/file
  */
class FileResponse(val libName:String, fileArg:String, mode:MODE) extends Response{
  val lib: Option[SoundLib] = Atmosphere.soundLibs.get(libName)
  val file: Option[Sound] = lib.get.sounds.get(fileArg)

  if(lib.nonEmpty)
     mode match {
      case PLAY => file.get.play
      case PAUSE => file.get.pause
      case LOOP => file.get.loop
      case _ => file.get.play
    }

  override val html: String = {
    Atmosphere.generateHTML(libName, lib.get)
  }
}
