package com.koorung.kotlinplayground.baekjoon

fun main() {
    with(System.`in`.bufferedReader()) {
        val (n, m) = readLine().split(" ").map { it.toInt() }  // 바구니 개수
        val intArray = IntArray(n) { 0 }
        repeat(m) {
            val (a, b, c) = readLine().split(" ").map { it.toInt() }
            (a - 1 until b).forEach { intArray[it] = c }
        }
        println(intArray.joinToString(" "))
    }
}