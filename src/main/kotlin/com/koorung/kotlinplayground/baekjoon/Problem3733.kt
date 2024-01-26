package com.koorung.kotlinplayground.baekjoon

fun main() {
    while (true) {
        val (a, b) = (readlnOrNull() ?: break).split(" ").map { it.toInt() }
        println(b / (a + 1))
    }
}