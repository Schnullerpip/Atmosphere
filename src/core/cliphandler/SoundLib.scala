package core.cliphandler

import java.io.File
import javax.sound.sampled.{AudioSystem, Clip}

import utils.logger.Logger

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

  def log(msg:String, method:String = "play", location:String = "SoundLib.scala::Sound") = Logger(msg, location, method)

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
    clip start()

    isPaused = false
    isPlaying = true
    log("PLAY sound: " + file.getName)
  }

  def loop = {
    clip loop Clip.LOOP_CONTINUOUSLY

    isPaused = false
    isPlaying = true
    log("LOOP sound: " + file.getName, "loop")
  }

  def stop = {
    if(isPlaying){
      clip stop()
      clip setFramePosition 0

      isPlaying = false
      isPaused = false
      log("STOP sound: " + file.getName, "stop")
    }
  }

  def pause = {
    clip stop()

    isPlaying = false
    isPaused = true
    log("PAUSE sound: " + file.getName, "pause")
  }
}

