//package lab7
//
//import org.apache.commons.io.FileUtils
//import org.apache.log4j.PropertyConfigurator
//import java.io.File
//import java.nio.charset.Charset
//import java.util.*
//
//private fun <E> List<E>.takeSample(size: Int): List<E> {
//  val random = Random()
//  val count = this.size
//  return (1..size).map {
//    this[random.nextInt(count)]
//  }
//}
//
//fun prepareFiles(src: String, batches: List<Int>, prefix: String): Map<Int, String> {
//  val lines = FileUtils.readLines(File(src), Charset.defaultCharset())
//  return batches
//      .map { it to "results/lab7/$prefix/sample-$it.txt" }.toMap()
//      .onEach { (size, destination) ->
//        (1..size / 1_000_000).forEach {
//          val sample = lines.takeSample(1_000_000)
//          FileUtils.writeLines(File(destination), sample, true)
//        }
//        FileUtils.writeLines(File(destination), lines.takeSample(size % 1_000_000), true)
//      }
//}
//
//fun com.kpi.lubchenko.lab6.kotlin.main(args: Array<String>) {
//  PropertyConfigurator.configure("src/com.kpi.lubchenko.lab6.kotlin.main/resources/log4j.properties")
//  System.setProperty("hadoop.home.dir", "c:/winutil/")
//  FileUtils.deleteDirectory(File("results/lab7"))
//  val src = "src/com.kpi.lubchenko.lab6.kotlin.main/resources/access_log_Aug95"
//  val smallFiles = prepareFiles(src, listOf(500, 5000, 50_000, 500_000), "small")
//  val largeFiles = prepareFiles(src, listOf(50_000, 500_000), "big")
//  println("Files created")
//  try {
//    val results = listOf(
//        "Parallel Sort" to testParallelSort(smallFiles).also { println("Parallel sort completed") },
//        "Parallel Stream" to testParallelStream(smallFiles).also { println("Parallel stream completed") },
//        "External Sort" to testExternalSort(largeFiles).also { println("External sort completed") },
//        "Spark" to testSpark(largeFiles).also { println("Spark sort completed") }
//    )
//    buildChart(results.slice(0..1), "Small Dataset")
//    buildChart(results.slice(2..3), "Big Dataset")
//  } finally {
//    FileUtils.deleteDirectory(File("results/lab7"))
//  }
//}
