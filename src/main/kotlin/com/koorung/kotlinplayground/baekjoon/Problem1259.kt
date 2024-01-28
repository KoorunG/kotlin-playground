package com.koorung.kotlinplayground.baekjoon

fun main() {
    while(true) {
        val n = readln()
        if(n == "0") break
        println(if(n == n.reversed()) "yes" else "no")
    }
}