package com.koorung.kotlinplayground.baekjoon

fun main() = List(readln().toInt()) { val (a, b) = readln().split(' ').map { it.toInt() }
    a to b }.sortedWith { o1, o2 -> if(o1.second - o2.second == 0) o1.first - o2.first else o1.second - o2.second }.forEach { println("${it.first} ${it.second}") }