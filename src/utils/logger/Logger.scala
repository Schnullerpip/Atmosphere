package utils.logger

import java.io.{File, FileWriter}
import java.util.Calendar

/**
  * Created by julian on 04-Mar-17.
  */
object Logger{
  val undefinedString = "UNDEFINED"
  val defaultLogPath = "tmp/AtmosphereLOG.txt"
  val time: Calendar = Calendar.getInstance()

  private var logger:Logger = _
  //handles the singleton
  def default(path:String = defaultLogPath, consoleOut:Boolean = true):Logger= {
    if(logger == null)
      logger = new Logger(path, consoleOut)
    logger
  }

  //auxiliary constructor
  def apply(path:String, consoleOut:Boolean) = new Logger(path, consoleOut)

  def log(msg:String, location:String, method:String): Unit = default().log(msg, location, method)
  def log(msg:String, location:Option[String] = None, method : Option[String] = None): Unit = default().log(msg, location, method)

  def apply(msg:String, location:String, method:String): Unit = log(msg, location, method)
  def apply(msg:String, location:Option[String] = None, method:Option[String] = None): Unit = log(msg, location, method)
}


class Logger(private val logFilePath:String = Logger.defaultLogPath, val consoleOut:Boolean = true) {

  //indicate delimiter in logfile, and don't save writer
  {
    val writer = new FileWriter(new File(logFilePath), true)
    writer.write("\n----------------------------------------")
    writer.close()
  }

  //mark new log session
  log("STARTING NEW LOG SESSION", "Logger.scala", "Constructor")

  //standard log
  def log(msg:String, location:String, method:String): Unit = log(msg, Some(location), Some(method))

  //standard log with optional location and method specification
  def log(msg:String, location:Option[String] = None, method : Option[String] = None): Unit ={
    //the string that will be logged
    val out = {
      "[" +
        Logger.time.getTime +
      "]::[" +
      location.getOrElse(Logger.undefinedString) +
      "]::[" +
      method.getOrElse(Logger.undefinedString) +
      "] -> " +
      msg + "\n"
    }

    //optionally write to console
    if(consoleOut)println(out)

    //write to logfile
    val writer = new FileWriter(new File(logFilePath), true)
    writer.write(out)
    writer.close()
  }
}
