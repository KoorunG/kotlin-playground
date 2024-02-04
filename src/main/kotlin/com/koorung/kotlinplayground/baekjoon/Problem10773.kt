package com.koorung.kotlinplayground.baekjoon

//val list = List(readln().toInt()) { readln().toInt() }
//val (zero, rest) = list.withIndex().partition { it.value == 0 }
//
//print((if (zero.size == rest.size) 0
//else if (zero.isEmpty()) rest.sumOf { it.value }
//else {
//    rest.partition { it.index < zero.maxOf { z -> z.index } }.let { (f, s) ->
//        f.subList(0, f.size - zero.size).sumOf { it.value } + s.sumOf { it.value }
//    }
//}).toLong())

fun main() {
    val l = mutableListOf<Int>()
    repeat(readln().toInt()) {
        val n = readln().toInt()
        if(n == 0) l.removeLast() else l.add(n)
    }
    print(l.sum().toLong())
}