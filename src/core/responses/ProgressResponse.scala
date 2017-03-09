package core.responses

import main.Atmosphere

/**
  * Created by julian on 08-Mar-17.
  * The response for sending back the progress of each file in a lib
  * how long are they playing yet?
  * Since this response is not supposed to actually return html code it overrides the header
  * @param libName the name of the lib
  */
case class ProgressResponse(libName:String) extends Response{

  override val HEAD = "HTTP/1.1 200 OK\r\n\r\n"

  override def html: String = {
    Atmosphere.soundLibs(libName).sounds.map { ns =>
      if(ns._2.isPlaying){
        val fPos = ns._2.clip.getFramePosition % ns._2.clip.getFrameLength
        val fLen = ns._2.clip.getFrameLength
        val p = (fPos*1.0/fLen)*100.0
        if(p >= 100){
          ns._1 + ":" + 100 + ";"
        }else
          ns._1 + ":" + p + ";"
      }
      else ""
    }.mkString
  }
}
