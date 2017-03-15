package core.cliphandler

import java.io.File
import javax.sound.sampled.{AudioSystem, Clip, FloatControl}

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
  var isLooping = false
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
    isLooping = false
    isPlaying = true
    log("PLAY sound: " + file.getName)
  }

  def loop = {
    clip loop Clip.LOOP_CONTINUOUSLY

    isPaused = false
    isPlaying = true
    isLooping = true
    log("LOOP sound: " + file.getName, "loop")
  }

  def stop = {
    if(isPlaying){

      //create a thread, that continuously lowers the clips volume and finally stops it
      new Thread(){
        val gainControl: FloatControl = clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl]
        val previousVolume: Float = gainControl.getValue
        val range: Float = gainControl.getMaximum - gainControl.getMinimum
        override def run():Unit = {
          var i = 1
          while(i <= 10 && clip.isActive){
            gainControl.setValue((gainControl.getMaximum - range*i/10.0).asInstanceOf[Float])
            Thread.sleep(300)
            i += 1
          }
          if(clip isActive){
            clip stop()
            gainControl.setValue(previousVolume)
            clip setFramePosition 0
          }
        }
      }.start()


      isPlaying = false
      isLooping = false
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

  /**
    * pretty much does what it says
    * @param volume value between 0.0 and 1.0
  */
  def setVolume(volume:Float):Unit = {
    if (volume < 0f || volume > 1f)
      throw new IllegalArgumentException("Volume not valid: " + volume)
  }

}
