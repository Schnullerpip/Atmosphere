package core.responses

import main.Atmosphere

/**
  * Created by julian on 08-Mar-17.
  */
class ProgressResponse(libName:String) extends Response{

  override val HEAD = "HTTP/1.1 200 OK\r\n\r\n"
  override def html: String = {
    Atmosphere.soundLibs(libName).sounds.map { ns =>
      if(ns._2.isPlaying){
        val fPos = ns._2.clip.getFramePosition % ns._2.clip.getFrameLength
        val fLen = ns._2.clip.getFrameLength
        val p = (fPos*1.0/fLen)*100.0
        println(p)
        if(p >= 100){
          ns._1 + ":" + 100 + ";"
        }else
          ns._1 + ":" + p + ";"
      }
      else ""
    }.mkString
  }
}
