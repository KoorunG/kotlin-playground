package com.koorung.kotlinplayground.baekjoon

fun main() {
    val s = readln().toInt()
    var k = 1
    while(k < s) {
        val n = s - k
        if(k.toString().sumOf { it.toString().toInt() } == n) break
        k++
    }
    print(if(k == s) 0 else k)
}