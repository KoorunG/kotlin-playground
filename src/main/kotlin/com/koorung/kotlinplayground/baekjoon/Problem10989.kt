package com.koorung.kotlinplayground.baekjoon

fun main() {
    val l = mutableListOf<Int>()
    repeat(readln().toInt()) {
        l.add(readln().toInt())
    }
}