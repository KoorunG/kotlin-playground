package com.koorung.kotlinplayground.baekjoon

import kotlin.math.floor

fun main() {
    var h = 0
    val (n, m, b) = f()
    val l = mutableListOf<Int>()
    repeat(n) {l.addAll(f())}
    val a = floor(l.average()).toInt()
    val case1 = a * l.size - l.sum()
    val case2 = (a + 1) * l.size - l.sum()
    val pair1 = case1 * -2 to a
    val pair2 = case2 to a + 1
    if(b >= case2) {
        if(pair1.first < pair2.first) print("${pair1.first} ${pair1.second}") else print("${pair2.first} ${pair2.second}")
    } else {
        print("${pair1.first} ${pair1.second}")
    }

}

private fun f() = readln().split(' ').map { it.toInt() }