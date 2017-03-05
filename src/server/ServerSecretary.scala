package server

import java.net.ServerSocket
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
case class ServerSecretary(port:Int) {
  private var continue = true
  private var connections = List[SocketServerConnection]()
  log("Starting server on port" + port)
  private val socket :ServerSocket = new ServerSocket(port)
  log("Set up server socket: " + socket.getLocalSocketAddress + ". Now listening")

  def listen() = {
        while(continue){
          log("Server listening on port: " + port + " - waiting for connection")
          val incomingConnection = socket.accept()

          if(!connections.exists( c => c.socket.getInetAddress == incomingConnection.getInetAddress && c.isAlive)) {
            val connection = new SocketServerConnection(incomingConnection)
            connections = connection :: connections
            connection.start()
          }
        }
  }

  listen()

  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("ServerSecretary.scala"), Some("Constructor"))
    else Logger(msg, "ServerSecretary.scala", method)
  }
}
