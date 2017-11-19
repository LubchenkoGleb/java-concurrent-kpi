package lab5

import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.locks.ReentrantLock

class Queue2 {
  private var q = ArrayBlockingQueue<String>(10)

  companion object {
    private val lock = ReentrantLock()
    private val bufferIsFull = lock.newCondition()
    private val bufferIsEmpty = lock.newCondition()
  }

  fun put(message: String) {
    lock.lock()
    try {
      this.q.add(message)
      bufferIsEmpty.signalAll()
    } finally {
      lock.unlock()
    }
  }

  fun take(): String {
    val v: String
    lock.lock()
    try {
      while (q.isEmpty()) {
        println("${Thread.currentThread().name} Buffer is empty, waiting")
        bufferIsEmpty.await()
      }
      v = this.q.poll()
      bufferIsFull.signalAll()
    } finally {
      lock.unlock()
    }
    return v
  }
}

class Producer3(private val queue: Queue2) : Runnable {
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


class Consumer3(private val queue: Queue2) : Runnable {
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
  val drop = Queue2()
  Thread(Producer3(drop)).start()
  Thread(Consumer3(drop)).start()
}