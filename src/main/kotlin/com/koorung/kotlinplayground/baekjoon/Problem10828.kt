package com.koorung.kotlinplayground.baekjoon

fun main() {
    val st = mutableListOf<Int>()
    repeat(readln().toInt()) {
        when(val s = readln()) {
            "top" -> println(if(st.isNotEmpty()) st.last() else -1)
            "size" -> println(st.size)
            "empty" -> println(if(st.isNotEmpty()) 0 else 1)
            "pop" -> println(if(st.isNotEmpty()) st.removeLast() else -1)
            else -> st.add(s.split(" ").component2().toInt())
        }
    }
}