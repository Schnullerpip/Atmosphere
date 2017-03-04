package core.cliphandler

import java.io.File
import javax.sound.sampled.{AudioSystem, Clip}

/**
  * Created by julian on 04-Mar-17.
  */
case class SoundLib(map:Map[String, Sound]){
  val sounds:Map[String, Sound] = map

  def apply(clip:String):Sound = sounds(clip)
}

object Sound{
  def apply(path:String) = new Sound(path)
}
class Sound(path:String){
  val (prefix, fileType) = ("sounds/DnD/", ".wav")
  val clip:Clip = {
    val file = new File(prefix + path + fileType)
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

object AmbientSound {
  def apply(path:String) = new AmbientSound(path)
}
class AmbientSound(path:String) extends Sound(path){
  override val prefix = "sounds/DnD/ambient/"
  override val clip:Clip = {
    val file = new File(prefix + path + fileType)
    if(file.exists()){
      val sound = AudioSystem.getAudioInputStream(file)
      val c = AudioSystem.getClip()
      c open sound
      c
    }else
      null
  }
}
