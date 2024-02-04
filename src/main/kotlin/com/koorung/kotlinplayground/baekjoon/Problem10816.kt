package com.koorung.kotlinplayground.baekjoon

import java.util.StringTokenizer

fun main() {
    val br = System.`in`.bufferedReader()
    val sb = StringBuilder()
    br.readLine()
    val cards = IntArray(20_000_002)
//    br.readLine().split(" ").forEach { cards[it.toInt() + 10_000_000] += 1 }
    StringTokenizer(br.readLine()).also { while (it.hasMoreTokens()) cards[it.nextToken().toInt() + 10_000_000] += 1 }
    br.readLine()
//    br.readLine().split(" ").forEach { sb.append("${cards[it.toInt() + 10_000_000]} ") }
    StringTokenizer(br.readLine()).also { while (it.hasMoreTokens()) sb.append("${cards[it.nextToken().toInt() + 10_000_000]} ") }
    println(sb)
}