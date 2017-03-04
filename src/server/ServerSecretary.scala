package server

import java.net.ServerSocket

import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class ServerSecretary(val port:Int) {
  private var continue = true
  log("Starting server on port" + port)
  private val socket :ServerSocket = new ServerSocket(port)
  log("Set up server socket: " + socket.getLocalSocketAddress + ". Now listening")
  listen

  def listen = {
    val listenThread = new Thread(new Runnable {
      override def run() = {
        while(continue){
          log("Server listening on port: " + port + " - waiting for connection")

        }
      }
    })
  }

  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("ServerSecretary.scala"), Some("Constructor"))
    else Logger(msg, "ServerSecretary.scala", method)
  }


}
