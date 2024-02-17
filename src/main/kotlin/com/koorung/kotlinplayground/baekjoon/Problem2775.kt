package com.koorung.kotlinplayground.baekjoon

fun main() {
    repeat(readln().toInt()) {
        val k = readln().toInt()
        val n = readln().toInt()
        var arr = MutableList(k + 1) { MutableList(n) { 0 } }
        (0 until n).forEach { i -> arr[0][i] = i + 1 }
        (0 .. k).forEach { j -> arr[j][0] = 1 }
        (1 until n).forEach { i -> (1..k).forEach { j -> arr[j][i] = arr[j - 1][i] + arr[j][i - 1]} }
        println(arr[k][n - 1])
    }
}