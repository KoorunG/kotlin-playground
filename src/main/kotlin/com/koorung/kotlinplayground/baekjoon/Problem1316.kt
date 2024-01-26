package com.koorung.kotlinplayground.baekjoon

fun main() {
    val n = readln().toInt()
    var m = 0
    repeat(n) {if(isGroupWord(readln())) m++ }
    print(m)
}

fun isGroupWord(word: String) = word.zipWord().toList().size == word.zipWord().toSet().size

fun String.zipWord(): String {
    val ml = mutableListOf<Char>()
    this.forEach {
        if(ml.indexOf(it) == -1 || ml.last() != it) ml.add(it)
    }
    return ml.joinToString("")
}