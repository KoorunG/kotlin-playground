package com.koorung.kotlinplayground.baekjoon

fun main() {
    val s = readln().uppercase().groupBy { it }.map { it.value.size to it.key }
    val max = s.maxOf { it.first }
    val f = s.filter { it.first == max }
    print(if(f.size == 1) f.first().second else "?")
}