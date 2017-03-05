import java.io.File

import core.cliphandler.Sound
import utils.logger.Logger

/**
  * Created by julian on 04-Mar-17.
  */
object Playground extends App{

  val a = Sound(new File("C:\\Users\\julian\\IdeaProjects\\Atmosphere\\sounds\\DnD\\DND Tavern Ambience (152kbit_Opus).wav"))
  val b = Sound(new File("C:\\Users\\julian\\IdeaProjects\\Atmosphere\\sounds\\DnD\\Dragon Age Inquisition - Dragon Fight Music (192kbit_AAC).wav"))
  val c = Sound(new File("C:\\Users\\julian\\IdeaProjects\\Atmosphere\\sounds\\DnD\\Dragon Age_ Inquisition - Fereldan Frostback Battle Theme (The Hinterlands) (192kbit_AAC).wav"))
  val d = Sound(new File("C:\\Users\\julian\\IdeaProjects\\Atmosphere\\sounds\\DnD\\Dragon Age_ Inquisition Soundtrack - Combat - Trevor Morris (192kbit_AAC).wav"))
  val e = Sound(new File("C:\\Users\\julian\\IdeaProjects\\Atmosphere\\sounds\\DnD\\Skyrim Ambience - Whiterun_Fantasy Town_D&D White Noise (Relaxation_ASMR) (128kbit_AAC).wav"))

  Console.readLine()
}
