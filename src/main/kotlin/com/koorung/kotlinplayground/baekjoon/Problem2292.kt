package com.koorung.kotlinplayground.baekjoon

tailrec fun f(n: Int, acc: Int = 1): Int = if(n > 0) f(n - 1, acc + n * 6) else acc
fun main() {
    val n = readln().toInt()
    var i = 0
    while(n - f(i) > 0) { i++ }
    print(i + 1)
}