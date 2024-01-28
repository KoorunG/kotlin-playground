package com.koorung.kotlinplayground.baekjoon

fun main() {
    val n = readln().toInt()
    val words = mutableListOf<String>()
    repeat(n) {
        words.add(readln())
    }
    words.distinct()
        .sortedWith { o1, o2 ->
            val compareLength = (o1.length).compareTo(o2.length)
            val compareDictionary = o1.compareTo(o2)
            if(compareLength != 0) compareLength else compareDictionary
        }
        .forEach { println(it) }
}