package core.responses

import core.cliphandler.SoundLib
import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  * Lib/file
  */
class FileResponse(libAndFile:String) extends Response{
  val args: List[String] = libAndFile.split("/").toList

  val lib: Option[SoundLib] = Atmosphere.soundLibs.get(args.head)
  if (lib.nonEmpty) {
    val file = lib.get.sounds.get(args.last)
    if(args.size > 2){
      if(args(1) == "PAUSE")
        file.get.pause
      else if(args(1) == "LOOP")
        file.get.loop
    }else
      file.get.play
  }

  override val html: String = {
    Atmosphere.generateHTML(args.head, lib.get)
  }
}
