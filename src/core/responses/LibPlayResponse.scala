package core.responses

import core.cliphandler.Sound
import main.Atmosphere
import utils.logger.Logger

/**
  * Created by julian on 08-Mar-17.
  */

object LibPlayResponse{
  private var isWaiting = false
  private var uniquePlaylists:Map[String, LibPlayResponse] = Map()

  def apply(libName:String): LibPlayResponse = {
    if(uniquePlaylists.get(libName).nonEmpty)
      uniquePlaylists(libName)
    else{
      val newEntry = libName -> new LibPlayResponse(libName)
      uniquePlaylists = uniquePlaylists + newEntry
      newEntry._2
    }
  }
}

class LibPlayResponse(val libName:String) extends Response{
  val playlist: Seq[Sound] = Atmosphere.soundLibs(libName).sounds.values.toSeq
  private var currentlyPlaying:Seq[Sound] = Seq()
  private val playListTraverser = new Thread(){
    var continue = true

    override def run(): Unit = {
      Logger("playListTraverser[THREAD: " + getName + "] STARTED to play playlist: " + libName, "LibPlayResponse.scala", "run")
      loopPlaylist(playlist)()
      Logger("playListTraverser[THREAD: " + getName + "] STOPPED to play playlist: " + libName, "LibPlayResponse.scala", "run")
    }
  }

  def killPlaylist(): Unit = {
    Logger("Killing the whole playlist " + libName, "LibPlayResponse.scala", "killPlaylist")
    //currentlyPlaying foreach {_.stop}
    Atmosphere.soundLibs(libName).sounds.foreach(_._2.stop)
    playListTraverser.continue = false
    LibPlayResponse.uniquePlaylists = LibPlayResponse.uniquePlaylists.filter(entry => entry._1 != libName)
  }

  def playAll(): Unit = {
    if(currentlyPlaying.nonEmpty){
      Logger("Previous instance of playlist: " + libName + " was found", "LibPlayResponse", "playAll")
      killPlaylist()
    }
    Atmosphere.soundLibs(libName).sounds.foreach(_._2.stop)
    Logger("Playing the whole playlist " + libName, "LibPlayResponse.scala", "playAll")
    currentlyPlaying = playlist
    playlist foreach {_.loop}
  }

  def loopPlaylist(clips:Iterable[Sound])(contin:Boolean = playListTraverser.continue):Unit ={
    if(currentlyPlaying.nonEmpty){
      Logger("Previous instance of playlist: " + libName + " was found", "LibPlayResponse", "loopPlaylist")
      killPlaylist()
    }
    Atmosphere.soundLibs(libName).sounds.foreach(_._2.stop)

    rLoop(util.Random.shuffle(clips))(contin)
  }

  def rLoop(clips:Iterable[Sound])(contin:Boolean = playListTraverser.continue):Unit = {
    if(!contin)return
    Logger("playing: " + clips.head.clip + " in playlist: " + libName, "LibPlayResponse.scala", "rLoop")
    val clipLength = clips.head.clip.getMicrosecondLength/1000
    currentlyPlaying = Seq(clips.head)
    clips.head.play
    Thread.sleep(clipLength)
    if(clips.tail.nonEmpty) {
      rLoop(clips.tail)()
    }
    else{
      Logger("Repeating from the start", "LibPlayResponse.scala", "rLoop")
      rLoop(playlist)()
    }
  }

  def act(mode:MODE):LibPlayResponse = {
    if(playlist.nonEmpty){
      mode match {
        case PLAY =>
          playAll()
        case LOOP =>
          playListTraverser.start()
          Thread.sleep(200)
        case _ =>
          killPlaylist()
      }
    }
    this
  }

  override def html: String = {
    new IndexResponse().html //this loads the new index page
  }
}
