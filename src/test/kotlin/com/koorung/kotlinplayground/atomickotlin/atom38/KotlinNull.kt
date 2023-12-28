package com.koorung.kotlinplayground.atomickotlin.atom38

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class Nullable(
    val a: String,
    val b: Int,
    val c: Boolean
)

/**
 * 코틀린에서의 nulL 처리
 */
class KotlinNull {

    @Test
    fun `코틀린에서 nullable한 타입은 역참조를 할 수 없다`() {
        val nullable: Nullable? = Nullable("10", 20, false)

        // 0. nullable한 타입의 역참조는 컴파일 오류가 발생함
        // nullable.a

        // 1. if로 null처리
        if(nullable != null) {
            println(nullable.a)
        }

        // 2. 세이프티 콜
        println(nullable?.a)     // "10"
    }

    @Test
    fun `null일때 특별한 동작을 하고 싶으면 엘비스 연산자를 쓴다`(){
        val nullable: Nullable? = Nullable("10", 20, false)
        val nullable2: Nullable? = null

        assertThat(nullable?.a).isEqualTo("10")
        assertThat(nullable?.a ?: "널임").isEqualTo("10")

        // null일 경우 디폴트 값을 리턴하는 용도로 주로 사용한다.
        assertThat(nullable2?.b).isNull()
        assertThat(nullable2?.b ?: "널임").isEqualTo("널임")
    }

    @Test
    fun `!! 연산자를 사용하여 null이 아님을 단언할 수 있다`(){
        val isNotNull: Nullable? = Nullable("10", 20, true)
        val isNull: Nullable? = null

        assertThat(isNotNull!!.a).isEqualTo("10")
        // java.lang.NullPointerException이 발생한다.
        assertThrows<NullPointerException> { isNull!!.a }.printStackTrace()
    }

    @Test
    fun `코틀린에서 제공하는 Nullable 처리 함수`(){
        val nullable: String? = "a"
        val nullable2: String? = null
        val nullable3: String? = "      "

        assertThat(nullable.isNullOrEmpty()).isFalse()
        assertThat(nullable2.isNullOrEmpty()).isTrue()
        assertThat(nullable3.isNullOrBlank()).isTrue()
    }
}