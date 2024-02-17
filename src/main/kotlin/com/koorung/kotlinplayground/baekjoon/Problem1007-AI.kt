package com.koorung.kotlinplayground.baekjoon

import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

var N = 0
val coords = Array(20) { IntArray(2) }
val check = BooleanArray(20)
var result = Double.MAX_VALUE

fun solve(idx: Int, cnt: Int) {
    if (cnt == N / 2) {
        var vx = 0
        var vy = 0
        for (i in 0 until N) {
            if (check[i]) {
                vx -= coords[i][0]
                vy -= coords[i][1]
            } else {
                vx += coords[i][0]
                vy += coords[i][1]
            }
        }
        result = min(result, sqrt(vx.toDouble().pow(2.0) + vy.toDouble().pow(2.0)))
        return
    }
    for (i in idx until N) {
        if (!check[i]) {
            check[i] = true
            solve(i + 1, cnt + 1)
            check[i] = false
        }
    }
}

fun main() {
    repeat(readln().toInt()) {
        N = readln().toInt()
        for (i in 0 until N) {
            coords[i] = readln().split(' ').map { it.toInt() }.toIntArray()
            check[i] = false
        }
        solve(0, 0)
        println(result)
    }
}