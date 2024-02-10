package com.koorung.kotlinplayground.baekjoon

fun main() {
    readln()
    val a1 = readln().parseInput().sorted()
    readln()
    val a2 = readln().parseInput()
    a2.forEach { (println(a1.bSearch(it))) }
}

private fun String.parseInput() = this.split(" ").map { it.toLong() }

private fun List<Long>.bSearch(n: Long): Int {
    var left = 0
    var right = this.lastIndex
    while(left <= right) {
        val mid = (left + right) / 2
        when {
            this[mid] < n -> left = mid + 1
            this[mid] > n -> right = mid - 1
            this[mid] == n -> return 1
        }
    }
    return 0
}