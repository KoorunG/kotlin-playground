package com.koorung.kotlinplayground.baekjoon

fun main() {
    val sb = StringBuilder()
    val list = MutableList<Int?>(2_000_001){ null }
    repeat(readln().toInt()) {
        val s = readln().toInt()
        list[s + 1_000_000] = s
    }
    list.filterNotNull().forEach { sb.append("$it\n") }
    print(sb)
}