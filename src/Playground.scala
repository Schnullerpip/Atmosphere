import core.cliphandler.{AmbientSound, Sound, SoundLib}
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
object Playground extends App{
  val logger = Logger.default()
  logger.log("it works, does it?")
  
  val sl = SoundLib(Map{
    "test" -> AmbientSound("birds")
  })

  sl("test") play

  logger.log("finished?")
  Console.readLine()
}
