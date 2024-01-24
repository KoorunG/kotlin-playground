package com.koorung.kotlinplayground.baekjoon

fun main() {
    with(System.`in`.bufferedReader()) {
        val (n, m) = readLine().split(" ").map { it.toInt() }
        val array = IntArray(n) { it + 1 }
        repeat(m) {
            val (a, b) = readLine().split(" ").map { it.toInt() - 1 }
            array[a] = array[b].also { array[b] = array[a] }
        }
        println(array.joinToString(" "))
    }
}