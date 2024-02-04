package com.koorung.kotlinplayground.baekjoon

fun main() {
    val sb = StringBuilder()
    val k = listOf('(', ')', '[', ']')
    while (true) {
        val s = readln()
        if ("." == s) break
        var f = s.filter { k.indexOf(it) != -1 }
        while (true) {
            if(f.contains("()") || f.contains("[]")) {
                f = f.replace("[]", "").replace("()", "")
                continue
            }
            if (f.isNotBlank()) sb.append("no\n") else sb.append("yes\n")
            break
        }
    }
    println(sb)
}