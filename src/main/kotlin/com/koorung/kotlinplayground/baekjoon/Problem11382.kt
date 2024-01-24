package com.koorung.kotlinplayground.baekjoon

fun main() {
    with(System.`in`.bufferedReader()){
        println(readLine().trim().split(" ").sumOf { it.toLong() }) // toInt()로 하면 21억 이상의 숫자가 올때 NumberformatException 발생함을 주의
    }
}