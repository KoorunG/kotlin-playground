package com.koorung.kotlinplayground.baekjoon

fun main() {
    repeat(readln().toInt()) {
        val (f, s) = fib(readln().toInt())
        println("$f $s")
    }
}

tailrec fun fib(n: Int, a: Long = 0, b: Long = 1): Pair<Long, Long> =
    if(n == 0) b to a else fib(n - 1, a + b, a)
