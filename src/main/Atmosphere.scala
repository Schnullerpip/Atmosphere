package main

import java.io.{BufferedWriter, File, FileWriter}

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

  def generateHTMLFiles(soundLibs: Map[String, SoundLib]): Unit = {
    soundLibs foreach{lib => generateHTML(lib)}
  }

  def generateHTML(lib:(String, SoundLib)):String = {

    val newFile = new File("tmp/" + lib._1 + ".html")
    if (!newFile.exists())
      newFile.createNewFile()

    val br = new BufferedWriter(new FileWriter(newFile))

    val prototype = io.Source.fromFile("web/prototype.html")
    var text = try prototype.mkString finally prototype.close()

    val out = text.replaceAll("LIBNAME", lib._1).replaceFirst("LIBENTRIES", {
      for (s <- lib._2.sounds) yield
        "\t\t\t<tr><td>" +
          Prototype.buttonFile
            .replaceFirst(Prototype.hrefPlaceholder, "FILE/" + lib._1 + "/" + s._1)
            .replaceFirst(Prototype.btnPlaceholder, if (s._2.isPlaying) "btn-info" else "")
            .replaceFirst(Prototype.placeholder,
              {if(s._2.isPlaying)"<span class=\"glyphicon glyphicon-play\"></span>"
              //else if(s._2.isPaused)"<span class=\"glyphicon glyphicon-pause\"></span>"
              else ""} +
              s._1) +
          "</td><td>" +
          Prototype.options
            .replaceFirst(Prototype.playPlaceholder,"FILE/" + lib._1 + "/" + s._1)
            .replaceFirst(Prototype.pausePlaceholder, "FILE/PAUSE/" + lib._1 + "/" + s._1)
            .replaceFirst(Prototype.repeatPlaceholder, "FILE/LOOP/" + lib._1 + "/" + s._1) +
          "</td></tr>"
    }.mkString)
    br.write(out)
    br.close()
    out
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

    log("Generating html files...")
    generateHTMLFiles(soundLibs);
    log("Generating html files DONE")

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
  val hrefPlaceholder = "HREFPLACEHOLDER"
  val btnPlaceholder = "BTNPLACEHOLDER"
  val playPlaceholder = "PLAYPLACEHOLDER"
  val pausePlaceholder = "PAUSEPLACEHOLDER"
  val repeatPlaceholder = "REPEATPLACEHOLDER"

  val buttonFolder: String =  "<a class=\"btn btn-default btn-block\" href=\"" + hrefPlaceholder + "\">"+placeholder+"</a>"
  val buttonFile: String =    "<a class=\"btn btn-default btn-block " + btnPlaceholder + "\" href=\"" + hrefPlaceholder + "\">"+placeholder+"</a>"
  val options: String =
    s"""
      <div class="btn-group">
       <a class="btn btn-primary" href="$playPlaceholder">
          <span class="glyphicon glyphicon-play"></span>
       </a>
       <a class="btn btn-primary" href="$pausePlaceholder">
          <span class="glyphicon glyphicon-pause"></span>
       </a>
       <a class="btn btn-primary" href="$repeatPlaceholder">
          <span class="glyphicon glyphicon-repeat"></span>
       </a>
      </div>
    """.stripMargin
}