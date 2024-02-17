package com.koorung.kotlinplayground.baekjoon

fun main() {
    val (a, b, v) = readln().split(' ').map { it.toInt() }
    println(if((v % (a - b)) < (a - b)) v / (a - b) + 1 else v / (a - b) - a + 1)
}