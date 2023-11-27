package com.koorung.kotlinplayground.advancedkotlin.section2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Tests {

    // 1. 테스트마다 각각 인스턴스화를 하는 경우
//    @Test
//    fun `Person의 isKim 테스트`() {
//        val person = Person(name = "김재학")
//        assertThat(person.isKim).isTrue()
//    }
//
//    @Test
//    fun `Person의 maskingName 테스트`() {
//        val person = Person(name = "이재학")
//        assertThat(person.maskingName).isEqualTo("이**")
//    }

    // 2. 인스턴스화를 한번 하는 경우 (클래스의 프로퍼티에 디폴트값을 지정하는 방식으로)
    // 디폴트값을 이용하는 방식은 위험하다
//    private var initPerson = Person()
//
//    @Test
//    fun `초기에 인스턴스화한 객체로 Person의 isKim 테스트`() {
//        val person = initPerson.apply { this.name = "김재학" }
//        assertThat(person.isKim).isTrue()
//    }
//
//    @Test
//    fun `초기에 인스턴스화한 객체로 Person의 maskingName 테스트`() {
//        val person = initPerson.apply { this.name = "이재학" }
//        assertThat(person.maskingName).isEqualTo("이**")
//    }

    private val initPerson = Person()

    // 3. lateinit 키워드 사용
    @Test
    fun `lateinit 키워드를 사용한 Person의 isKim 테스트`() {
        val person = initPerson.apply { this.name = "김재학" }
        assertThat(person.isKim).isTrue()
    }

    @Test
    fun `lateinit 키워드를 사용한 Person의 maskingName 테스트`() {
        val person = initPerson.apply { this.name = "이재학" }
        assertThat(person.maskingName).isEqualTo("이**")
    }

    @Test
    fun `lateinit 키워드를 붙인 프로퍼티는 초기화하지 않으면 예외가 발생한다`() {
        assertThrows<UninitializedPropertyAccessException> {
            val person = initPerson
            assertThat(person.maskingName).isEqualTo("이**")
        }
    }

    @Test
    fun `by lazy 키워드 테스트`() {
        val person = Person3()
        println(person.name)        // 2초 후 프린트
        println(person.age)         // 바로 출력됨
    }
}