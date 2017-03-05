package server

import java.io.PrintWriter
import java.net.Socket

import main.{Atmosphere, Prototype}
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class SocketServerConnection(val socket:Socket) extends Thread{

  log("incoming connection accepted from: " + socket.getRemoteSocketAddress)

  override def run() = {
    log("Server connected to " + socket.getRemoteSocketAddress)

    //load the index file (html)
    val indexSource = io.Source.fromFile("web/index.html")
    var index = try indexSource.mkString finally indexSource.close()

    index = index.replaceFirst(Prototype.placeholder, {
      for( lib <- Atmosphere.soundLibs) yield "\t\t\t<tr><td>" +
        Prototype.buttonFolder.replaceFirst(Prototype.placeholder, lib._1 + " <span class=\"badge\">"+
          lib._2.size +"</span></a><br>") + "</td><td>" + Prototype.dropOptions + "</td></tr>"
    }.mkString)

    val pw = new PrintWriter(socket.getOutputStream)
    pw.write(Response.gen(index))
    pw.close()
    log("Server sent index to : " + socket.getRemoteSocketAddress)
  }

  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("SocketServerConnection.scala"), Some("Constructor"))
    else Logger(msg, "SocketServerConnection.scala", method)
  }

}

object Response {
  private val HEAD = "HTTP/1.1 200 OK\r\nContent-type: text/html\r\n\r\n"
  def gen(html:String):String = HEAD + html
}
