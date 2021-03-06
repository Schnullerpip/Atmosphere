package server

import java.io._
import java.net.Socket

import core.requestResolver.RequestResolver
import core.responses._
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class SocketServerConnection(val socket:Socket) extends Thread{

  log("incoming connection accepted from: " + socket.getRemoteSocketAddress, "Constructor")

  /**
    * parse http request and act accordingly
    * @param input the pure http header like: GET /index HTTP/1.1
    * @return a Response instance, that can generate the http response string*/
  def evaluateInput(input: String):Response = {

    //check if request is for index
    {
      val request = input.replaceAll("GET /", "").replaceAll("HTTP/.*\\..*", "")
      if ( request == " " || request == "index " )
        return IndexResponse()
      else if( request == "favicon.ico ") //TODO return a favicon when one exists
        return NotFoundResponse
    }

    //parse the input and reduce it to the relevant information - if successfully parsed call the response constructor
    import core.requestResolver.RequestResolver._
    val responseParsing: RequestResolver.ParseResult[() => Response] = parse(request, input)
    if(responseParsing.successful){
      val responseConstructor: () => Response = responseParsing.get
      responseConstructor()
    }else{
      NotFoundResponse
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
        NotFoundResponse
      }
    }

    val pw = new PrintWriter(socket.getOutputStream)
    pw write response.gen()
    pw close()
    in close()
    log("Server sent response to : " + socket.getRemoteSocketAddress)
    log("Connection closed to: " + socket.getRemoteSocketAddress)
  }

  //simplification for call to Logger
  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("SocketServerConnection.scala"), Some("run"))
    else Logger(msg, "SocketServerConnection.scala", method)
  }
}
