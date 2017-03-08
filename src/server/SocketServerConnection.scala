package server

import java.io._
import java.net.Socket

import core.requestResolver.RequestResolver._
import core.responses._
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class SocketServerConnection(val socket:Socket) extends Thread{

  log("incoming connection accepted from: " + socket.getRemoteSocketAddress, "Constructor")

  def evaluateInput(input: String):Response = {

    //check if request is for index
    {
      val request = input.replaceAll("GET /", "").replaceAll("HTTP/.*\\..*", "")
      if ( request == " " || request == "index " )
        return new IndexResponse()
      else if( request == "favicon.ico ") //TODO return a favicon when one exists
        return new NotFoundResponse()
    }

    //parse the input and reduce it to the relevant information - if successfully parsed call the response constructor
    val responseParsing = parse(request, input)
    if(responseParsing.successful){
      val responseConstructor = responseParsing.get
      responseConstructor()
    }else{
      new NotFoundResponse()
    }
  }

  override def run():Unit = {
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    log("Server connected to " + socket.getRemoteSocketAddress)

    val response = {
      val request = in.readLine()

      if(request != null){
        log("Routing input:" + request)
        evaluateInput(request)
      }else{
        new NotFoundResponse()
      }
    }

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
