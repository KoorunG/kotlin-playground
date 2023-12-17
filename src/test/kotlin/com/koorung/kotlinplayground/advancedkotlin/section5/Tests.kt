package com.koorung.kotlinplayground.advancedkotlin.section5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.full.cast
import kotlin.reflect.full.memberFunctions

class Tests {

    @Test
    fun `리플랙션 객체를 얻는 법`() {
        // 1. 클래스에서 리플랙션 객체를 얻는 법
        val kClass = Reflection::class
        val reflection = Reflection()

        // 2. 인스턴스에서 리플랙션 객체를 얻는 법 (단, 공변상태)
        val kClass1 = reflection::class

        // 3. Class.forName(클래스명) 으로 자바 리플랙션을 얻는 방법
        val javaClass = Class.forName("com.koorung.kotlinplayground.advancedkotlin.section5.Reflection")
        // 자바 리플랙선.kotlin -> 코틀린 리플랙션
        // 코틀린 리플랙선.java -> 자바 리플랙션
        val kClass2 = javaClass.kotlin

        assertThat(kClass.qualifiedName).isEqualTo(kClass1.qualifiedName)
        assertThat(kClass1.qualifiedName).isEqualTo(kClass2.qualifiedName)
        assertThat(kClass.qualifiedName).isEqualTo(kClass2.qualifiedName)
    }

    @Test
    fun `리플랙션 객체를 이용하여 함수를 호출`() {
        val goldFish = GoldFish("금붕이")

        // 리플랙션을 이용하여 GoldFish.print()를 호출하는 방법
        GoldFish::class.members.first {
            it.name == "print"
            /**
             * print()는 파라미터가 없는 것 처럼 보여도
             * 내부적으로 리플랙션에서는 '자기자신'이 파라미터로 들어가야 하기 때문에 (때문에 자기자신의 멤버에 접근할 수 있다)
             * goldFish를 파라미터로 넣어준다!
             */
        }.call(goldFish)

        val result = GoldFish::class.memberFunctions.first {
            it.name == "name"
        }.call(goldFish).let { String::class.cast(it) }
        assertThat(result).isEqualTo("금붕이")
    }

    @Test
    fun `클래스 내의 인자가 없고 리턴타입이 Unit인 모든 함수 호출`(){
        executeAll(ExecutableClass())
    }
}