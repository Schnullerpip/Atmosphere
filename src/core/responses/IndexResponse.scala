package core.responses

import main.{Atmosphere, Prototype}

/**
  * Created by julian on 05-Mar-17.
  */
class IndexResponse extends Response{
  override val html: String = {
    val indexSource = io.Source.fromFile("web/index.html")
    var index = try indexSource.mkString finally indexSource.close()

    index = index.replaceFirst(Prototype.placeholder, {
      for( lib <- Atmosphere.soundLibs) yield {
        val a = Prototype.buttonFolder.replaceFirst(Prototype.placeholder, lib._1 + " <span class=\"badge\">" +
            lib._2.size + "</span></a><br>")
        "\t\t\t<tr><td>" +
          Prototype.buttonFolder
            .replaceFirst(Prototype.hrefPlaceholder, "LIB=" + lib._1)
            .replaceFirst(Prototype.placeholder, lib._1 + " <span class=\"badge\">" +
            lib._2.size + "</span></a><br>") + "</td><td>" + Prototype.options + "</td></tr>"
      }}.mkString)
    index
  }
}
