package core.cliphandler

import java.io.File
import javax.sound.sampled.{AudioSystem, Clip}

/**
  * Created by julian on 04-Mar-17.
  */
case class SoundLib(map:Map[String, Sound]){
  val sounds:Map[String, Sound] = map

  def apply(clip:String):Sound = sounds(clip)

  def size = sounds.size
}

case class Sound(file:File){

  private def clip():Clip = {
    if(file.exists()){
      val sound = AudioSystem.getAudioInputStream(file)
      val c = AudioSystem.getClip()
      c open sound
      c
    }else
      null
  }

  def play = {
    clip setFramePosition 0
    clip start()
  }

  def loop = clip loop Clip.LOOP_CONTINUOUSLY

  def stop = {
    clip stop()
    clip setFramePosition 0
  }

  def pause = clip stop()
}

