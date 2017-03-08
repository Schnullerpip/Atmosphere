package core.responses

import core.cliphandler.{Sound, SoundLib}
import main.Atmosphere
import utils.logger.Logger

/**
  * Created by julian on 08-Mar-17.
  */
class LibPlayResponse(libName:String) extends Response{
  val lib: Option[SoundLib] = Atmosphere.soundLibs.get(libName)
  if(lib.nonEmpty){
    val playlist = lib.get.sounds.values

    val watchman = new Thread {
      def rPlay(clips:Iterable[Sound]):Unit = {
        if(clips.isEmpty)
          return
        Logger("rPlay: " + clips.head, "LibPlayResponse.scala", "rPlay")
        Logger("rPlay rest: " + clips.tail, "LibPlayResponse.scala", "rPlay")
        val clipLength = clips.head.clip.getMicrosecondLength/1000
        clips.head.play
        Thread.sleep(clipLength)
        if(clips.nonEmpty)
          rPlay(playlist.tail)
      }

      override def run():Unit = {
        rPlay(playlist)
      }
    }

    watchman.start()
  }



  override val html: String = {
    new IndexResponse().html //this loads the new index page
  }
}
