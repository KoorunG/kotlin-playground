package com.koorung.kotlinplayground.baekjoon

fun main() {
    with(System.`in`.bufferedReader()) {
        val (n, m) = readLine().split(" ").map { it.toInt() }
        // 1 ... n 으로 바구니 초기화
        val array = IntArray(n) { it + 1 }

        // m번 반복
        repeat(m) {
            val (a, b) = readLine().split(" ").map { it.toInt() - 1 }
            // a ~ b까지 바구니를 역순으로
            array.reverse(a, b + 1)
        }

        array.joinToString(" ").also { println(it) }
    }
}