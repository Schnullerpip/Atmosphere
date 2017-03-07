import core.requestResolver.RequestResolver

/**
  * Created by julian on 04-Mar-17.
  */
object Playground extends App{


  var r = "GET /FILE=blu\\bla.wav;MODE=PLAY HTTP/1.1"
  r = "GET / HTTP/1.1"
  r = "GET /FILE=DAInquisition.wav;LIB=DnD;MODE=PLAY HTTP/1.1"
  println(r)

  println(RequestResolver.parse(RequestResolver.request, r).get)

}
