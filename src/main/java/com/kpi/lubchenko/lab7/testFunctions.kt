//package lab7
//
//import com.google.code.externalsorting.ExternalSort
//import org.apache.commons.io.FileUtils
//import org.apache.spark.api.java.JavaSparkContext
//import java.io.File
//import java.nio.charset.Charset
//import java.util.*
//import kotlin.streams.toList
//import kotlin.system.measureTimeMillis
//
//fun testParallelSort(files: Map<Int, String>) =
//    files.map { (size, src) ->
//      val destination = File("results/lab7/par/$size.txt")
//      FileUtils.touch(destination)
//      val lines = FileUtils.readLines(File(src), Charset.defaultCharset()).toTypedArray()
//      size to measureTimeMillis {
//        Arrays.parallelSort(lines) // PARALLEL SORT
//        FileUtils.writeLines(destination, lines.toList())
//      }
//    }.toMap()
//
//fun testParallelStream(files: Map<Int, String>) =
//    files.map { (size, src) ->
//      val destination = File("results/lab7/stream/$size")
//      FileUtils.touch(destination)
//      val lines = FileUtils.readLines(File(src), Charset.defaultCharset())
//      size to measureTimeMillis {
//        val sorted = lines.parallelStream().sorted().toList() // PARALLEL STREAM
//        FileUtils.writeLines(destination, sorted)
//      }
//    }.toMap()
//
//fun testExternalSort(files: Map<Int, String>) =
//    files.map { (size, src) ->
//      val destination = File("results/lab7/ext/$size")
//      FileUtils.touch(destination)
//      size to measureTimeMillis {
//        ExternalSort.mergeSortedFiles( // EXTERNAL SORT
//            ExternalSort.sortInBatch(File(src)),
//            destination
//        )
//      }
//    }.toMap()
//
//fun testSpark(files: Map<Int, String>) = run {
//  val sc = JavaSparkContext("local[*]", "Sorting example")
//  files.map { (size, src) ->
//    val lines = sc.textFile(src, 2)
//    size to measureTimeMillis {
//      val sorted = lines.sortBy({ it }, true, 2) // APACHE SPARK SORT
//      sorted.saveAsTextFile("results/lab7/spark/$size")
//    }
//  }.toMap()
//}