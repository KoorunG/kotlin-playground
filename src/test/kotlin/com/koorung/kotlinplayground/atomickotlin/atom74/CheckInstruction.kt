package com.koorung.kotlinplayground.atomickotlin.atom74

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

/**
 * 검사명령
 * 코틀린은 "계약에 의한 설계(Design by Contract)"를 지원하는 몇가지 기능이 존재한다.
 * 1. require(), requireNotNull() - 사전조건 검사
 * 2. check() - 사후조건 검사
 * (assert()도 존재하지만 위의 두 개를 우선적으로 사용한다.)
 */

data class Month (
    val monthNumber: Int
) {
    init {
        // require()은 사전조건에 맞지 않는 값이 오면 IllegalArgumentException을 발생시킨다.
        require(monthNumber in 1..12) {
            // lazyMessage
            "[REQUIRE] Month out of range :: $monthNumber"
        }
    }
}

fun operateWithMonth(month: Month) {
    // 다양한 연산들..
//    if (month.monthNumber > 12) {
//        println("Month out of range :: $month")
//    }

    val result: Int = month.monthNumber * 2

    // 함수 끝에서 check()
    check(result >= 20) {
        "[CHECK] Month out of range :: ${month.monthNumber}"
    }
}

class CheckInstruction {

    @Test
    fun `require를 이용한 사전조건 검사`() {
        // 클라이언트의 parameter 정합성을 체크한다.
        // IllegalArgumentException 를 발생시킨다.
        assertThrows<IllegalArgumentException> { Month(13) }.message?.let {
            assertThat(it).isEqualTo("[REQUIRE] Month out of range :: 13")
        }
        assertDoesNotThrow { Month(12) }
    }

    @Test
    fun `check를 이용한 사후조건 검사`() {
        // 서버의 operation의 결과를 체크할 때 사용한다.
        // IllegalStateException 를 발생시킨다.
        assertThrows<IllegalStateException> { operateWithMonth(Month(9)) }.message?.let {
            assertThat(it).isEqualTo("[CHECK] Month out of range :: 9")
        }
    }
}