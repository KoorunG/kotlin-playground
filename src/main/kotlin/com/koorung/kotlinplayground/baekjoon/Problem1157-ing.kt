package com.koorung.kotlinplayground.baekjoon

fun main() = print(readln().uppercase().groupBy { it }.map { it.value.size to it.key }.groupBy { it.first }.maxBy { it.key }.value.let { if(it.size >= 2) "?" else it.first().second })
