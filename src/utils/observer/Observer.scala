package utils.observer

/**
  * Created by julian on 08-Mar-17.
  */
trait Observer {

  val action:() => Unit

  def NOTIFY(act:()=>Unit = action): Unit = action()
}
