package com.koorung.kotlinplayground.baekjoon

fun main() {
    val (k, n) = readln().split(" ").map { it.toInt() }
    val list = List(k) { readln().toInt() }
    var len = list.max()
    var sum = 0
    while (len-- > 0) {
        if (sum == list.sumOf { it / len }) continue
        sum = list.sumOf { it / len }
        if (sum >= n) break
    }
    println(len)
}