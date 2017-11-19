package lab5

import java.util.concurrent.TimeUnit
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors

class Broker {
  private val queue = ArrayBlockingQueue<Int?>(100)
  var continueProducing = true

  fun put(data: Int) {
    queue.put(data)
  }

  fun get(): Int? {
    return queue.poll(1, TimeUnit.SECONDS)
  }
}

class Producer(private val broker: Broker) : Runnable {
  override fun run() {
    (1..5).forEach {
      println("lab5.Producer produced: $it")
      Thread.sleep(100)
      broker.put(it)
    }
    broker.continueProducing = false
    println("lab5.Producer finished it's job")
  }
}

class Consumer(private val broker: Broker) : Runnable {
  override fun run() {
    var data = broker.get()
    while (broker.continueProducing || data != null) {
      Thread.sleep(100)
      println("lab5.Consumer consumed: $data")
      data = broker.get()
    }
    println("lab5.Consumer finished it's job")
  }
}

fun main(args: Array<String>) {
  val broker = Broker()

  val threadPool = Executors.newFixedThreadPool(3)

  threadPool.execute(Consumer(broker))
  threadPool.execute(Consumer(broker))
  val producerStatus = threadPool.submit(Producer(broker))

  producerStatus.get()
  threadPool.shutdown()
}