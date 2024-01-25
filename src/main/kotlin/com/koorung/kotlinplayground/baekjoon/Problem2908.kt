package com.koorung.kotlinplayground.baekjoon

import kotlin.math.max

fun main() {
    val (a, b) = readln().split(" ")
    print(max(a.reversed().toInt(), b.reversed().toInt()))
}