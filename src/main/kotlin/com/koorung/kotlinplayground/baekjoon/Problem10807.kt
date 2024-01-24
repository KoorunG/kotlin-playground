package com.koorung.kotlinplayground.baekjoon

fun main() {
    with(System.`in`.bufferedReader()) {
        val first = readLine().toInt()
        val seconds = readLine().split(" ").map { it.toInt() }.toList()
        val third = readLine().toInt()
        println(seconds.count { it == third })
    }
}