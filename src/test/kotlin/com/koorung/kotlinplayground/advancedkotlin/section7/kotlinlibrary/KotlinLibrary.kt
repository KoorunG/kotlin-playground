package com.koorung.kotlinplayground.advancedkotlin.section7.kotlinlibrary

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

/**
 * 코틀린의 유용한 라이브러리 목록
 */
class KotlinLibrary {

    @Test
    fun `repeat 블록은 블록 안의 코드를 N번 반복한다`() {
        repeat(10) { println("${it}번 반복합니다!") } // it은 0부터 N-1까지 바인딩됨
    }

    @Test
    fun `TODO함수는 Nothing 타입을 던지기 때문에 인지하기 쉽다`() {
        assertThatThrownBy { TODO("구현해야해요") }
    }

    @Test
    fun `measureTimeMills는 시간측정을 간편하게 할 수 있다`() {
        val timeMillis = measureTimeMillis {
            runBlocking {
                println("무언가 시간이 걸리는 일")
                delay(1000)
            }
        }
        println("timeMillis :::: $timeMillis ms")
        assertThat(timeMillis).isGreaterThan(1000)
    }

    @Test
    fun `require는 제약조건을 간편하게 설정한다 주로 파라미터 검증에 쓰인다`() {
        fun number(num: Number) = num

        assertThatThrownBy {
            require(number(30) != 30) { "30이 와야 합니다!" }
        }
    }


    @Test
    fun `check는 IllegalStateException을 던진다 주로 상태나 멤버변수 검증에 쓰인다`() {
        val num = 30

        assertThatThrownBy {
            check(num != 30) { "30이 와야 합니다!" }
        }
    }

    @Test
    fun `runCatching은 Result라는 객체를 반환하며 예외처리를 간단하게 구성할 수 있다`(){
        fun number(num: Number) = num
        val result = runCatching {
            require(number(30) != 30) { "30이 와야 합니다!" }
        }
        println("result :::: $result")

        assertThat(result.isSuccess).isFalse()
        assertThat(result.isFailure).isTrue()

        result.onFailure {
            println("실패했으니 예외처리를 해주세요!")
        }
        assertThat(result.exceptionOrNull()?.localizedMessage).isEqualTo("30이 와야 합니다!")

        result.map {  }
        result.mapCatching {  }
    }
}