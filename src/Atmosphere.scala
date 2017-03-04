import utils.logger.Logger
import core.cliphandler._

/**
  * Created by julian on 04-Mar-17.
  */
object Atmosphere {

  def logprototype(msg:String, method:String = null): Unit = {
    if(method == null) Logger(msg, Some("Atmosphere.scala"), None)
    else Logger(msg, "Atmosphere.scala", method)
  }

  var ambientLib : SoundLib = _
  var combatLib : SoundLib = _
  var tavernLib : SoundLib = _

  def main(args : Array[String]): Unit ={
    def log(msg:String) = logprototype(msg, "main")

    log("Starting application Atmosphere")

    log("loading ambient sound library")
    ambientLib = SoundLib(Map[String, Sound](
        "birds" -> AmbientSound("birds"),
        "crickets" -> AmbientSound("crickets"),
        "fire" -> AmbientSound("fire"),
        "people" -> AmbientSound("people"),
        "rain" -> AmbientSound("rain"),
        "thunder" -> AmbientSound("thunder"),
        "waves" -> AmbientSound("waves"),
        "wind" -> AmbientSound("wind"),
        "sbowl" -> AmbientSound("sbowl"),
        "whitenoise" -> AmbientSound("whitenoise")
      ))
    log("loading ambient sound library - done")

    log("loading tavern sound library")
    tavernLib = SoundLib(Map(

    ))
    log("loading tavern sound library - done")

    log("loading combat sound library")
    combatLib = SoundLib(Map(

    ))
    log("loading combat sound library - done")

    log("Starting Server")
  }
}
