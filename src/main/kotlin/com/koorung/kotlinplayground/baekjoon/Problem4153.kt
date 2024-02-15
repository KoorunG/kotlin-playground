package com.koorung.kotlinplayground.baekjoon

import kotlin.math.pow

fun main() {
    while (true) {
        val s = readln()
        if(s ==  "0 0 0") break
        val l = s.split(' ').map { it.toDouble().pow(2) }
        val (max, others) = l.partition { it == l.max() }
        println(if(max.sum() == others.sum()) "right" else "wrong")
    }
}
