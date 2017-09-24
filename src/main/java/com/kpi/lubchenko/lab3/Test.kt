package com.kpi.lubchenko.lab3

import java.util.concurrent.Callable
import java.util.concurrent.ForkJoinPool

fun foxMultiply(a: List<List<Double>>, b: List<List<Double>>): List<List<Double>> {

    val c = List(a.size, { MutableList(a.size, { 0.0 }) })
    ForkJoinPool.commonPool().invokeAll(
            MutableList(a.size * a.size, {
                val i = it / a.size
                val j = it % a.size
                Callable {
                    for (shift in 0..a.lastIndex)
                        c[i][j] += a[i][shift] * b[shift][j]
                }
            })
    )
    return c
}

fun main(args: Array<String>) {
    println("Hello, world!")
    val a1: List<Double> = listOf(1.0,2.0,3.0)
    val a2: List<Double> = listOf(4.0, 5.0, 6.0)
    val a3: List<Double> = listOf(7.0, 8.0, 9.0)
    val a: List<List<Double>> = listOf(a1, a2, a3)

    val b1: List<Double> = listOf(1.0,2.0,3.0)
    val b2: List<Double> = listOf(4.0, 5.0, 6.0)
    val b3: List<Double> = listOf(7.0, 8.0, 9.0)
    val b: List<List<Double>> = listOf(b1, b2, b3)
//        x[0] = x[1] + x[2]
//
    var foxMultiply = foxMultiply(a, b)
    println(foxMultiply)
}