package com.koorung.kotlinplayground.baekjoon

fun main() {
    val q = ArrayDeque<Int>()
    val sb = StringBuilder()
    repeat(readln().toInt()) {
        val s = readln().split(" ")
        when(s[0]) {
            "push_back" -> q.addLast(s[1].toInt())
            "push_front" -> q.addFirst(s[1].toInt())
            "front" -> sb.append("${q.firstOrNull()?:-1}\n")
            "back" -> sb.append("${q.lastOrNull()?:-1}\n")
            "size" -> sb.append("${q.size}\n")
            "empty" -> sb.append("${if(q.isEmpty()) 1 else 0}\n")
            "pop_front" -> sb.append("${q.removeFirstOrNull()?:-1}\n")
            "pop_back" -> sb.append("${q.removeLastOrNull()?:-1}\n")
        }
    }
    print(sb)
}