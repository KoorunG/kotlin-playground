package com.koorung.kotlinplayground.baekjoon

import kotlin.math.pow
import kotlin.math.sqrt

data class Point(var x: Int, var y: Int) {
    var isSelected = false
}

private fun minDistance(points: List<Point>, pointsCount: Int): Double {
    var minDistance = Double.MAX_VALUE

    fun calculateMinDistance(): Double {
        var sumX = 0
        var sumY = 0
        for (point in points) {
            if (point.isSelected) {
                sumX -= point.x
                sumY -= point.y
            } else {
                sumX += point.x
                sumY += point.y
            }
        }
        return minOf(minDistance, sqrt(sumX.toDouble().pow(2.0) + sumY.toDouble().pow(2.0)))
    }

    fun updateMinDistance(index: Int, count: Int) {
        if (count == pointsCount / 2) {
            minDistance = calculateMinDistance()
            return
        }
        for (i in index until pointsCount) {
            if (!points[i].isSelected) {
                points[i].isSelected = true
                updateMinDistance(i + 1, count + 1)
                points[i].isSelected = false
            }
        }
    }

    updateMinDistance(0, 0)

    return minDistance
}


fun main() {
    repeat(readln().toInt()) {
        val pointsCount = readln().toInt()
        val points = List(pointsCount) {
            val (x, y) = readln().split(' ').map { it.toInt() }
            Point(x, y)
        }
        println(minDistance(points, pointsCount))
    }
}