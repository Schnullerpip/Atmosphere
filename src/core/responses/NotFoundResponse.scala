package core.responses

/**
  * Created by julian on 05-Mar-17.
  */
class NotFoundResponse extends Response{
  override val HEAD = "HTTP/1.1 404 Not Found"
  override val html: String = "<h1> 404 Not Found</h>"
}
