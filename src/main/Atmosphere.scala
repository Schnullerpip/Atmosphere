package main

import java.io.File

import core.cliphandler._
import core.datafetch.DataFetch
import server.ServerSecretary
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
object Atmosphere {

  private val port = 50777
  private val soundsRootPath = "sounds"

  var soundLibs: Map[String, SoundLib] = Map[String, SoundLib]()

  def logprototype(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("Atmosphere.scala"), None)
    else Logger(msg, "Atmosphere.scala", method)
  }

  def main(args : Array[String]): Unit ={
    def log(msg:String) = logprototype(msg, "main")

    log("Starting application Atmosphere")

    def recursiveLoad(root:File):Unit = {
      val rootName = root.getName
      //get files
      val (files, folders) = DataFetch.filesAndFolders(root)

      //load the library
      if(files.nonEmpty){
        log("Loading " + rootName + " library")
        //create map containing all the .wav files
        var sounds = files filter (f => f.getName.contains(".wav")) map (s => s.getName -> Sound(s)) toMap;
        log("Loading" + rootName + " library - done")

        //introduce the new soundLib to the soundLibs map
        soundLibs = soundLibs + (root.getName -> SoundLib(sounds))
      }
      //go on recursively
      folders.foreach(recursiveLoad)
    }

    log("Traversing the library starting at: [" + soundsRootPath + "] to load the .wav files")
    recursiveLoad(new File(soundsRootPath))

    var statusOutput = ""
    soundLibs foreach { e =>
      statusOutput += "; " + e._1
    }

    log("Loaded the libraries:" + statusOutput)
    log("Generating index file according to loaded libraries")
    log("Starting Server")
    ServerSecretary(port)

  }
}

object Prototype{
  val placeholder = "PLACEHOLDER"
  val buttonFolder = "<button type=\"button\" class=\"btn btn-default btn-block\">PLACEHOLDER</button>"
  val buttonFile = "<button type=\"button\" class=\"btn btn-default\">PLACEHOLDER</button>"
  val dropOptions =
    "<div class=\"btn-group\">\n" +
      "<button type=\"button\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-play\"></span></button>\n" +
      "<button type=\"button\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-pause\"></span></button>\n" +
    "</div>"
}