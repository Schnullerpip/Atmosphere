package server

import java.io.{BufferedWriter, File, FileWriter}
import java.net.{InetAddress, ServerSocket, Socket}

import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  * the core server providing a listening socket, that can be connected to
  * an instance of this class is used to establish SocketServerConnections
  */
case class ServerSecretary(port:Int) {
  private var continue = true
  private var connections = List[SocketServerConnection]()

  log("Starting server on port" + port)
  private val socket :ServerSocket = new ServerSocket(port)

  log("Set up server socket: " + InetAddress.getLocalHost + ":" + port  +" Now listening")

  //write the link (ipadresse+port) to a tmp file so user can grab it from there
  val linkFile = new File("tmp/"+InetAddress.getLocalHost.toString.replaceFirst(".*/", "")+":"+port)
  if(!linkFile.exists())
    linkFile.createNewFile()

  def listen() = {
        while(continue){
          log("Server listening on port: " + port + " - waiting for connection", "listen")
          val incomingConnection: Socket = socket.accept()

          if(!connections.exists( c => (c.socket.getInetAddress == incomingConnection.getInetAddress) && c.isAlive)) {
            val connection = new SocketServerConnection(incomingConnection)
            connections = connection :: connections
            connection.start()
          }
        }
  }

  listen()

  //simplification for call to Logger
  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("ServerSecretary.scala"), Some("Constructor"))
    else Logger(msg, "ServerSecretary.scala", method)
  }
}
