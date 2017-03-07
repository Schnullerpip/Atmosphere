package core.responses

/**
  * Created by julian on 05-Mar-17.
  */
trait TYPE
case object LIB extends TYPE
case object FILE extends TYPE
case object NONE extends TYPE
case object INDEX extends TYPE

trait MODE
case object PLAY extends MODE
case object PAUSE extends MODE
case object LOOP extends MODE

trait Response {
  val HEAD = "HTTP/1.1 200 OK\r\nContent-type: text/html\r\n\r\n"
  val html:String
  def gen() = {
    HEAD + html
  }
}
