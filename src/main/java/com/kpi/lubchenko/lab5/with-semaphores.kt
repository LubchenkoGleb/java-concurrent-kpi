package lab5

import java.util.Random
import java.util.concurrent.Semaphore

class Queue {
  private lateinit var message: String

  companion object {
    private val semProd = Semaphore(1)
    private val semCon = Semaphore(0)
  }

  fun put(message: String) {
    semProd.acquire()
    this.message = message
    semCon.release()
  }

  fun take(): String {
    semCon.acquire()
    semProd.release()
    return message
  }
}

class Producer2(private val queue: Queue) : Runnable {
  override fun run() {
    val importantInfo = listOf("Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too")
    val random = Random()

    importantInfo.forEach {
      queue.put(it)
      Thread.sleep(random.nextInt(500).toLong())
    }

    queue.put("DONE")
  }
}


class Consumer2(private val queue: Queue) : Runnable {
  override fun run() {
    val random = Random()
    var message = queue.take()
    while (message != "DONE") {
      println("Message received: $message")
      Thread.sleep(random.nextInt(500).toLong())
      message = queue.take()
    }
  }
}

fun main(args: Array<String>) {
  val drop = Queue()
  Thread(Producer2(drop)).start()
  Thread(Consumer2(drop)).start()
}