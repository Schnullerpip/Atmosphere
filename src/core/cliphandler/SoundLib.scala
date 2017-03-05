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
  var isPaused = false

  lazy val clip:Clip = {
    if(file.exists()){
      val sound = AudioSystem.getAudioInputStream(file)
      val c = AudioSystem.getClip()
      c open sound
      c
    }else
      null
  }

  def play :Unit= {
    if(isPlaying){
      stop
      return
    }
    if(!isPaused)
      clip setFramePosition 0
    isPaused = false
    clip start()
    isPlaying = true
  }

  def loop = {
    isPaused = false
    clip loop Clip.LOOP_CONTINUOUSLY
    isPlaying = true
  }

  def stop = {
    pause
    clip setFramePosition 0
  }

  def pause = {
    clip stop()
    isPlaying = false
    isPaused = true
  }
}

