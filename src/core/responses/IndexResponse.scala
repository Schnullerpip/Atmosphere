package core.responses

import main.{Atmosphere, Prototype}

/**
  * Created by julian on 05-Mar-17.
  */
class IndexResponse extends Response{
  override def html: String = {
    val indexSource = io.Source.fromFile("web/index.html")
    var index = try indexSource.mkString finally indexSource.close()

    index = index.replaceFirst(Prototype.placeholder, {
      for( lib <- Atmosphere.soundLibs) yield {
        val isPlaying = lib._2.sounds.exists(_._2.isPlaying)
        "\t\t\t<tr><td>" +
          Prototype.buttonFolder
            .replaceFirst(Prototype.btnPlaceholder, {if(isPlaying) "btn-info" else ""})
            .replaceFirst(Prototype.hrefPlaceholder, "LIB=" + lib._1)
            .replaceFirst(Prototype.placeholder,
              {if(isPlaying)"<span class=\"glyphicon glyphicon-play\"></span>" else ""} +
              lib._1 + " <span class=\"badge\">" + lib._2.size + "</span></a><br>") +
          "</td><td>" +
            Prototype.options
              .replaceFirst(Prototype.playPlaceholder, "LIB=" + lib._1 + ";MODE=PLAY")
              .replaceFirst(Prototype.pausePlaceholder, "LIB=" + lib._1 + ";MODE=PAUSE")
              .replaceFirst(Prototype.repeatPlaceholder, "LIB=" + lib._1 + ";MODE=LOOP")+
          "</td></tr>"
      }}.mkString)
    index
  }
}
