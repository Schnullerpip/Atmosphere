package server

import java.io.ObjectOutputStream
import java.net.Socket

import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
class SocketServerConnection(val socket:Socket) extends Thread{
  private val oos : ObjectOutputStream = new ObjectOutputStream(socket getOutputStream)
  log("incoming connection accepted")

  override def run = {
    log("Server connected to " + socket.getInetAddress + " " + socket.getRemoteSocketAddress)
  }

  def log(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("SocketServerConnection.scala"), Some("Constructor"))
    else Logger(msg, "SocketServerConnection.scala", method)
  }

}
