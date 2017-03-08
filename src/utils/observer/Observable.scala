package utils.observer

/**
  * Created by julian on 08-Mar-17.
  */
trait Observable {
  var observers: List[Observer]

  def notifyObservers(): Unit = observers foreach {
    _.NOTIFY()
  }

  def register(observer: Observer): Unit = {
    observers = observer :: observers
  }
}

