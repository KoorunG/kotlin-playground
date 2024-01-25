package com.koorung.kotlinplayground.baekjoon

fun main() {
    val dialMap = mapOf(
        listOf('A', 'B', 'C') to 3,
        listOf('D', 'E', 'F') to 4,
        listOf('G', 'H', 'I') to 5,
        listOf('J', 'K', 'L') to 6,
        listOf('M', 'N', 'O') to 7,
        listOf('P', 'Q', 'R', 'S') to 8,
        listOf('T', 'U', 'V') to 9,
        listOf('W', 'X', 'Y', 'Z') to 10
    )
    print(readln().toCharArray().map { c -> dialMap.filter { (k, _) -> k.indexOf(c) != -1 }.values }.sumOf { it.first() })
}