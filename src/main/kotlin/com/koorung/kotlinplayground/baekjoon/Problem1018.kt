package com.koorung.kotlinplayground.baekjoon

fun main() {
    val (n, m) = readln().split(" ").map { it.toInt() }
    val chess = List(n){ readlnOrNull() ?: "\n" }
    var min = Int.MAX_VALUE
    for (i in 0 .. n - 8) {
        for (j in 0 .. m - 8) {
            var startW = 0
            var startB = 0
            for(k in i until i + 8) {
                for(l in j until j + 8) {
                    if(isEven(k, l) && isWhite(chess, k, l)) startB++
                    if(isEven(k, l) && isBlack(chess, k, l)) startW++
                    if(!isEven(k, l) && isWhite(chess, k, l)) startW++
                    if(!isEven(k, l) && isBlack(chess, k, l)) startB++
                }
            }
            min = minOf(min, startW, startB)
        }
    }
    println(min)
}

private fun isWhite(chess: List<String>, k: Int, l: Int) = chess[k][l] == 'W'
private fun isBlack(chess: List<String>, k: Int, l: Int) = chess[k][l] == 'B'
private fun isEven(k: Int, l: Int) = (k + l) % 2 == 0