package com.koorung.kotlinplayground.advancedkotlin.section3

import com.koorung.kotlinplayground.advancedkotlin.section3.Operator.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Tests {

    @Test
    fun `코틀린에서 고차함수에 함수파라미터를 전달하는 방법`(){
        // 1. 람다 (리턴타입을 적을 수 없다, return 키워드를 쓸 수 없다)
        val lambda = compute(5, 3) { a, b -> a + b }
        // 2. 익명함수 (리턴타입을 적을 수 있다, return 키워드를 쓸 수 있다)
//        val annonymous = compute(5, 3, fun(a, b): Int {return a + b})
        val annonymous = compute(5, 3, fun(a, b) = a + b)   // (가장 축약한 형태)

        // 위 두 경우를 함숫값 or 함수리터럴 이라고 표현한다.
        assertThat(lambda).isEqualTo(8)
        assertThat(lambda).isEqualTo(annonymous)
    }

    @Test
    fun `Int 리스트를 전달하여 이터레이션을 돌면서 출력`(){
        iterate((1..10).toList(), fun(n){println(n)})   // 익명함수
        iterate((1..10).toList()){ println(it) }        // 람다
    }

    @Test
    fun `두 숫자와 연산자를 입력하여 계산하는 함수 테스트`() {
        assertThat(calculate(1, 3, '+')).isEqualTo(4)
        assertThat(calculate(1, 3, '*')).isEqualTo(3)
        assertThat(calculate(1, 3, '-')).isEqualTo(-2)
        assertThat(calculate(1, 3, '/')).isEqualTo(0)
        assertThat(
            assertThrows<IllegalArgumentException> { calculate(1, 3, '&') }.message
        ).isEqualTo("들어올 수 없는 연산자입니다 ::: [&]")
    }

    @Test
    fun `두 숫자와 연산자를 입력하여 계산하는 함수 테스트(enum class 사용)`() {
        // enum class로 선언했기 때문에 잘못된 연산자 자체가 오지 않음
        assertThat(calculate2(1, 3, PLUS)).isEqualTo(4)
        assertThat(calculate2(1, 3, MULTIPLY)).isEqualTo(3)
        assertThat(calculate2(1, 3, MINUS)).isEqualTo(-2)
        assertThat(calculate2(1, 3, DIVIDE)).isEqualTo(0)
    }
}