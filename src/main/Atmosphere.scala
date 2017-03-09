package main

import java.io.{BufferedWriter, File, FileWriter}

import core.Prototype
import core.cliphandler._
import core.datafetch.DataFetch
import server.ServerSecretary
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
object Atmosphere {

  //static fixed references
  private val port = 50777
  private val soundsRootPath = "sounds"

  //Map containing all the soundlibs
  var soundLibs: Map[String, SoundLib] = Map[String, SoundLib]()

  //simplification for calls to Logger object
  def logprototype(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("Atmosphere.scala"), None)
    else Logger(msg, "Atmosphere.scala", method)
  }

  /**
    * generates .html files for all the libs given
    * @param soundLibs the map containing all the string -> SoundLib mappings*/
  def generateHTMLFiles(soundLibs: Map[String, SoundLib]): Unit = {
    soundLibs foreach{lib => generateHTML(lib)}
  }

  /**
    * generates the .html file for a specific soundLib
    * @param lib a tuple String -> SoundLib
    * @return the content of the generated .html file, just in case you want to use it directly*/
  def generateHTML(lib:(String, SoundLib)):String = {

    val newFile = new File("tmp/" + lib._1 + ".html")
    if (!newFile.exists())
      newFile.createNewFile()

    val bw = new BufferedWriter(new FileWriter(newFile))

    val prototype = io.Source.fromFile("web/prototype.html")
    var text = try prototype.mkString finally prototype.close()

    val out = text.replaceAll("LIBNAME", lib._1).replaceFirst("LIBENTRIES", {
      for (s <- lib._2.sounds) yield
        "\t\t\t<tr><td>" +
          Prototype.buttonFile
            .replaceFirst(Prototype.hrefPlaceholder, "LIB=" + lib._1 + ";FILE=" + s._1 + ";MODE=PLAY")
            .replaceFirst(Prototype.btnPlaceholder, if (s._2.isPlaying) "btn-info" else "")
            .replaceFirst(Prototype.progressPlaceholder, if(s._2.isPlaying)Prototype.progressBar else "")
            .replaceFirst(Prototype.idPlaceHolder, s._1)
            .replaceFirst(Prototype.placeholder,
              {if(s._2.isLooping)" <span class=\"glyphicon glyphicon-repeat\"></span>"
              else if(s._2.isPlaying)" <span class=\"glyphicon glyphicon-play\"></span>"
              else if(s._2.isPaused)" <span class=\"glyphicon glyphicon-pause\"></span>"
              else ""} +
              s._1) +
          "</td><td>" +
          Prototype.options
            .replaceFirst(Prototype.playPlaceholder,  "LIB=" + lib._1 + ";FILE=" + s._1 + ";MODE=PLAY")
            .replaceFirst(Prototype.pausePlaceholder, "LIB=" + lib._1 + ";FILE=" + s._1 + ";MODE=PAUSE")
            .replaceFirst(Prototype.repeatPlaceholder,"LIB=" + lib._1 + ";FILE=" + s._1 + ";MODE=LOOP") +
          "</td></tr>"
    }.mkString)
    bw.write(out)
    bw.close()
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
        log("Loading library " + rootName)
        //create map containing all the .wav files
        var sounds = files filter (f => f.getName.contains(".wav")) map (s => s.getName -> Sound(s)) toMap;
        log("Loading library " + rootName + " -  DONE")

        //introduce the new soundLib to the soundLibs map
        soundLibs = soundLibs + (root.getName -> SoundLib(sounds))
      }
      //go on recursively
      folders.foreach(recursiveLoad)
    }

    log("Traversing the library starting at: [" + soundsRootPath + "] to load the .wav files")
    recursiveLoad(new File(soundsRootPath))

    log("Generating html files...")
    generateHTMLFiles(soundLibs)
    log("Generating html files - DONE")

    var statusOutput = ""
    soundLibs foreach { e =>
      statusOutput += e._1 + "; "
    }

    log("Loaded the libraries:" + statusOutput)
    log("Starting Server")
    ServerSecretary(port)
  }
}
