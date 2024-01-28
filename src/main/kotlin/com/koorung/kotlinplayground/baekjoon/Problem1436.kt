package com.koorung.kotlinplayground.baekjoon

fun main() {
    val s = readln()
    List(3_000_000){ it }
        .asSequence()
        .filter { it.toString().contains("666") }
        .filterIndexed { index, _ -> (s.toInt() - 1) == index }
        .first()
        .also { print(it) }
}