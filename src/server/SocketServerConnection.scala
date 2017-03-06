package server

import java.io._
import java.net.Socket

import core.cliphandler.SoundLib
import core.responses._
import main.{Atmosphere, Prototype}
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class SocketServerConnection(val socket:Socket) extends Thread{

  log("incoming connection accepted from: " + socket.getRemoteSocketAddress, "Constructor")

  def evaluateInput(input: String):Response = {
    var request = input.replaceFirst("GET /", "").replaceFirst(" HTTP/.*", "")
    if(request == " " || request.contains("index")){ //index
      new IndexResponse()
    }else {
      var library:Option[SoundLib] = null
      //check if a lib or a file is requested
      if(request.contains("FILE")){ //FILE/Lib/file
        request = request.replaceAll("LIB/", "")
        new FileResponse(request.replaceAll("FILE/", ""))
      }else if(request.contains("LIB") && {
        request = request.replaceAll("LIB/", "")
        library = Atmosphere.soundLibs.get(request)
        library.nonEmpty
      }){
        new LibResponse(request)
      }else{
        new NotFoundResponse()
      }
    }
  }

  override def run() = {
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    log("Server connected to " + socket.getRemoteSocketAddress)

    var input = in.readLine()
    log("Routing input:" + input)
    val response = evaluateInput(input)

    val pw = new PrintWriter(socket.getOutputStream)
    pw.write(response.gen())
    pw.close()
    in.close()
    log("Server sent response to : " + socket.getRemoteSocketAddress)

    log("Connection closed to: " + socket.getRemoteSocketAddress)
  }

  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("SocketServerConnection.scala"), Some("run"))
    else Logger(msg, "SocketServerConnection.scala", method)
  }
}
