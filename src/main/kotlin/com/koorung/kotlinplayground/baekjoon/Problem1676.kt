package com.koorung.kotlinplayground.baekjoon

import java.math.BigInteger


fun main() {
    val n = readln().toInt()
    val factorial = factorial(n).toString()
    val trimEnd = factorial.trimEnd { it == '0' }
    print(factorial.length - trimEnd.length)

}

private tailrec fun factorial(n: Int, acc: BigInteger = 1.toBigInteger()): BigInteger =
    if (n <= 0) acc else factorial(n - 1, acc * n.toBigInteger())