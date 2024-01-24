package com.koorung.kotlinplayground.baekjoon

fun main() {
    with(System.`in`.bufferedReader()) {
        val n = List(30) { it + 1 }
        val m = mutableListOf<Int>()
        repeat(28) {
            readLine().toInt().also { m.add(it) }
        }
        val(f, s) = n.filter { it !in m }
        print(String.format("%s\n%s", f, s))
    }
}