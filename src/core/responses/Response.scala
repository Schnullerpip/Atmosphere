package core.responses

/**
  * Created by julian on 05-Mar-17.
  */
trait Response {
  val HEAD = "HTTP/1.1 200 OK\r\nContent-type: text/html\r\n\r\n"
  val html:String
  def gen() = {
    HEAD + html
  }
}
