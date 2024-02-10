package com.koorung.kotlinplayground.baekjoon

fun main() {
    val m = mutableListOf<Pair<Int, Int>>()
    val sb = StringBuilder()
    repeat(readln().toInt()) {
        val (a, b) = readln().split(" ").map { it.toInt() }
        m.add(a to b)
    }
    m.sortedWith { o1, o2 -> if (o1.first == o2.first) o1.second - o2.second else o1.first - o2.first }.forEach { sb.append("${it.first} ${it.second}\n") }
    print(sb)
}