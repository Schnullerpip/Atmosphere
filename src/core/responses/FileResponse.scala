package core.responses

import core.cliphandler.{Sound, SoundLib}
import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  * The response for accesing the clip API
  */

object FileResponse{
  def apply(libName:String, fileArg:String, mode:MODE) = new FileResponse(libName, fileArg, mode)
}

/**
  * Access to the clip API
  * @param libName the name of the lib that contains the respective file
  * @param fileArg the name of the actual file that is to be accessed
  * @param mode specifies in which way the clip is supposed to be played/stopped
  * */
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

  override def html: String = {
    Atmosphere.generateHTML(libName, lib.get)
  }
}
