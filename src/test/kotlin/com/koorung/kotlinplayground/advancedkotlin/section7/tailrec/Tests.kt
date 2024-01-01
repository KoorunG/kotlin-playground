package com.koorung.kotlinplayground.advancedkotlin.section7.tailrec

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Tests {

    @Test
    fun `일반 재귀함수로 팩토리얼 구현하기`() {
        val timeMillis = measureTimeMillis {
            val result = factorialV1(50)
//            assertThat(result).isEqualTo(120)
        }
        println("timeMillis :::: $timeMillis ms")
    }

    @Test
    fun `꼬리재귀로 팩토리얼 구현하기`() {
        val timeMillis = measureTimeMillis {
            val result = factorialV2(50)
//            assertThat(result).isEqualTo(120)
        }
        println("timeMillis :::: $timeMillis ms")
    }

    @Test
    fun `인라인 클래스 테스트`() {
        val key = Key("비밀번호")
        // key 프로퍼티 타입으로 인라이닝 되어 들어간다..!
        //
        val inlineKey = InlineKey("비밀번호")
    }

    @Test
    fun `Id를 인라인 제네릭 클래스로 만들어 구분짓기`() {
        // 인라인 클래스로 구현하면 컴파일단계에서 검증할 수 있다..!
        val handle = handle(Id<User>(3L), Id<Book>(5L))
        assertThat(handle).isEqualTo("userId :: 3, bookId :: 5")
    }
}