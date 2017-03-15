package core.responses

import javax.sound.sampled.{LineEvent, LineListener}

import core.cliphandler.Sound
import main.Atmosphere
import utils.logger.Logger

/**
  * Created by julian on 08-Mar-17.
  */

object LibPlayResponse {
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

class LibPlayResponse(val libName:String) extends Response with LineListener{
  val playlist: Seq[Sound] = util.Random.shuffle(Atmosphere.soundLibs(libName).sounds.values.toSeq).toList
  private var currentlyPlaying:Seq[Sound] = Seq()
  private var rest:Seq[Sound] = Seq()
  private var continue = true

  def killPlaylist(): Unit = {
    Logger("Killing the whole playlist " + libName, "LibPlayResponse.scala", "killPlaylist")
    continue = false

    //currentlyPlaying foreach {_.stop}
    Atmosphere.soundLibs(libName).sounds.foreach(_._2.stop)

    //remove this playlist out of the global list of playlists
    LibPlayResponse.uniquePlaylists = LibPlayResponse.uniquePlaylists.filter(entry => entry._1 != libName)
  }

  def playHead = {
    currentlyPlaying.head.clip.addLineListener(this)
    currentlyPlaying.head.play
  }

  def playAll = {
    if(currentlyPlaying.nonEmpty){
      Logger("Previous instance of playlist: " + libName + " was found", "LibPlayResponse", "playAll")
      killPlaylist()
    }
    Atmosphere.soundLibs(libName).sounds.foreach(_._2.stop)
    Logger("Playing the whole playlist " + libName, "LibPlayResponse.scala", "playAll")
    currentlyPlaying = playlist
    playlist foreach {_.loop}
  }

  def loopPlaylist = {
    if(currentlyPlaying.nonEmpty){
      Logger("Previous instance of playlist: " + libName + " was found", "LibPlayResponse", "loopPlaylist")
      killPlaylist()
    }
    Atmosphere.soundLibs(libName).sounds.foreach(_._2.stop)

    currentlyPlaying = Seq(playlist.head)
    rest = playlist.tail
    playHead
  }

  def act(mode:MODE):LibPlayResponse = {
    if(playlist.nonEmpty){
      mode match {
        case PLAY =>
          playAll
        case LOOP =>
          loopPlaylist
        case _ =>
          killPlaylist()
      }
    }
    this
  }

  override def html: String = {
    new IndexResponse().html //this loads the new index page
  }

  override def update(event: LineEvent): Unit = {
    event.getType match {
      case LineEvent.Type.STOP if !currentlyPlaying.head.isPaused =>
        currentlyPlaying.head.clip.removeLineListener(this)
        if(continue){
          if(rest.nonEmpty){
            currentlyPlaying = Seq(rest.head)
            rest = rest.tail
            playHead
          } else
            loopPlaylist
        }
      case _ =>
    }
  }
}
