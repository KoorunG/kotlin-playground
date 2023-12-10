package com.koorung.kotlinplayground.coroutine.section1

import kotlinx.coroutines.delay

fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] ::: $str")
}

suspend fun apiCall1(result: Int): Int {
    println("apiCall1 START")
    delay(1_000L)
    println("apiCall1 END")
    return result
}

suspend fun apiCall2(result: Int): Int {
    println("apiCall2 START")
    delay(3_000L)
    println("apiCall2 END")
    return result
}