package server

import java.net.ServerSocket

import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class ServerSecretary(val port:Int) {
  log("starting server on port" + port, "Constructor")
  private val socket :ServerSocket = new ServerSocket(port)
  log("set up server socket: " + socket.getLocalSocketAddress(), "Constructor")
  listen

  def listen = {
    
  }

  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("ServerSecretary.scala"), None)
    else Logger(msg, "ServerSecretary.scala", method)
  }


}
