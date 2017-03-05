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

  var isPlaying = false

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
    isPlaying = true
  }

  def loop = {
    clip loop Clip.LOOP_CONTINUOUSLY
    isPlaying = true
  }

  def stop = {
    clip stop()
    clip setFramePosition 0
    isPlaying = false
  }

  def pause = {
    clip stop()
    isPlaying = false
  }
}

