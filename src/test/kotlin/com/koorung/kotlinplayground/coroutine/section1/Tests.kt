package com.koorung.kotlinplayground.coroutine.section1

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.jupiter.api.Test

/**
 * suspend 함수
 * : 이 키워드가 붙어야 다른 suspend 함수를 호출할 수 있다.
 * : (yield가 대표적인 내장 suspend 함수)
 */
suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    /**
     * yield 함수
     * : 현재 코루틴을 중지하고 다른 코루틴이 실행되도록 한다.
     */
    yield()
    printWithThread("${num1 + num2}")
}

fun printWithThread(str: String) {
    println("[${Thread.currentThread().name}] ::: $str")
}

class CoroutineTests {

    /**
     * runBlocking 블록
     * : 일반루틴 세계와 코루틴 세계를 연결
     * : 블록안의 내용으로 새로운 코루틴을 생성한다.
     */
    @Test
    fun `코루틴의 runBlocking 블록 사용`() = runBlocking {
        printWithThread("START")
        /**
         * launch 블록
         * : 반환값이 없는 코루틴을 생성한다.
         */
        launch {
            newRoutine()
        }
        yield()
        printWithThread("END")
    }
}