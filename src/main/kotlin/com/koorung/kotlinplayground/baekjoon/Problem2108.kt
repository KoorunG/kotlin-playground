package com.koorung.kotlinplayground.baekjoon

import kotlin.math.roundToInt

fun main() {
    val arr = IntArray(8_001) { 4_001 }
    repeat(readln().toInt()) {
        val s = readln().toInt()
        arr[s + 4_000]++
    }
    val l = arr.mapIndexed { i, n -> (i - 4000) to n}.filter { it.second != 4001 }.map { it.first to it.second - 4001 }
    val a = mutableListOf<Int>()
    val f = l.map { it.first }
    l.forEach { k -> repeat(k.second) { a.add(k.first)} }
    println(a.average().roundToInt())
    println(a[a.size / 2])
    println(l.filter { it.second == l.maxOf { m -> m.second } }.let { if(it.size >= 2) it[1] else it[0] }.first)
    println(f[l.size - 1] - f[0])
}