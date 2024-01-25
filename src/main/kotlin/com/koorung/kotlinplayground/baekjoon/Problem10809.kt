package com.koorung.kotlinplayground.baekjoon

fun main() {
    val s = readln()
    print(List(26){ (it + 97).toChar() }.map { s.indexOf(it) }.joinToString(" "))
}