package core.requestResolver

import core.responses._

import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by julian on 07-Mar-17.
  */
object RequestResolver extends JavaTokenParsers{


  /**
    * parsers the http request for FILE, LIB, and MODE arguments
    * @return returns a lambda that constructs the respective Response, so the caller of this method
    *         stays in control of the time and place where the actual Response ins created
    * */
  def request:Parser[() => Response] =
    (("GET" ~> "/") ~> ("LIB" ~> "=" ~> ident)) ~
    ((";FILE=" ~> """\w[\w\d]+\.wav""".r)?) ~
    (((";" ~> "MODE" ~> "=" ~> """(PLAY|PAUSE|LOOP)""".r)?) <~ "HTTP" <~ "/" <~ """\d\.\d""".r) ^^ {
    case lib ~ None ~ None => () => new LibResponse(lib)
    case lib ~ Some(name) ~ mode => () => new FileResponse(lib, name, mode match {
      case Some("PLAY") => PLAY
      case Some("PAUSE") => PAUSE
      case Some("LOOP") => LOOP
      case _ => PLAY
    })
    case lib ~ None ~ mode => () => new LibPlayResponse(lib)
    case _ => () => new NotFoundResponse()
  }
}
