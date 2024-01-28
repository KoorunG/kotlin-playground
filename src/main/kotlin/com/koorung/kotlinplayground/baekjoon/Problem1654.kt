package com.koorung.kotlinplayground.baekjoon

fun main() {
    val (k, n) = readln().split(" ").map { it.toInt() }
    val list = List(k) { readln().toLong() }
    var low = 1L
    var high = list.max()
    while (low <= high) {
        val mid = (low + high) / 2
        if (list.sumOf { it / mid } >= n) low = mid + 1 else high = mid - 1
    }
    print(high)
}

// 이진탐색 예제
fun binarySearch(list: List<Int>, target: Int): Int {
    var low = 0
    var high = list.lastIndex
    while (low <= high) {
        val mid = (low + high) / 2
        when {
            list[mid] == target -> return mid
            list[mid] < target -> low = mid + 1
            list[mid] > target -> high = mid - 1
        }
    }
    return -1
}