package com.koorung.kotlinplayground.baekjoon

fun main() {
    val s = readln()
    print(if(s.isBlank()) 0 else s.trim().split(" ").count())
}