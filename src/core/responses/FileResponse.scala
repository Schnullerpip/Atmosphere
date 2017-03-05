package core.responses

import main.Atmosphere

/**
  * Created by julian on 05-Mar-17.
  * Lib/file
  */
class FileResponse(libAndFile:String) extends Response{
  override val html: String = {
    val args = libAndFile.split("/").toList
    if (args.size == 2) {
      val lib = Atmosphere.soundLibs.get(args.head)
      if (lib.nonEmpty) {
        val file = lib.get.sounds.get(args(1))
        file.get.play
      }
    }
    ""//TODO an actual response?
  }
}
