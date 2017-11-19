//package lab7
//
//import org.jfree.chart.ChartFactory
//import org.jfree.chart.ChartPanel
//import org.jfree.chart.plot.PlotOrientation
//import org.jfree.data.category.DefaultCategoryDataset
//import org.jfree.ui.ApplicationFrame
//import org.jfree.ui.RefineryUtilities
//import org.jfree.chart.ChartUtilities
//import java.io.File
//
//class LineChart(
//    applicationTitle: String,
//    chartTitle: String,
//    results: List<Pair<String, Map<Int, Long>>>) : ApplicationFrame(applicationTitle) {
//
//  init {
//    val lineChart = ChartFactory.createLineChart(
//        chartTitle,
//        "Number of processors", "Time, ms",
//        createDataset(results),
//        PlotOrientation.VERTICAL,
//        true, true, false)
//
//    val chartPanel = ChartPanel(lineChart)
//    chartPanel.preferredSize = java.awt.Dimension(800, 600)
//    contentPane = chartPanel
//
//    val file = File("results/lab7-$chartTitle.jpeg")
//    ChartUtilities.saveChartAsJPEG(file, lineChart, 800, 600)
//  }
//
//  private fun createDataset(
//      results: List<Pair<String, Map<Int, Long>>>): DefaultCategoryDataset {
//    val dataset = DefaultCategoryDataset()
//    results.forEach { (name, vals) ->
//      vals.forEach { (size, time) ->
//        dataset.addValue(time, name, size)
//      }
//    }
//    return dataset
//  }
//}
//
//fun buildChart(results: List<Pair<String, Map<Int, Long>>>, name: String) {
//  with(LineChart("Sorting", name, results)) {
//    pack()
//    RefineryUtilities.centerFrameOnScreen(this)
//    isVisible = true
//  }
//}