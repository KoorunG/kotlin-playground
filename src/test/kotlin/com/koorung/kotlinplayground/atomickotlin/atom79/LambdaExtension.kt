package com.koorung.kotlinplayground.atomickotlin.atom79

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * 1. 두 파라미터를 받는 람다를 리턴
 */
val introduce1: (String, Int) -> String = { name, age ->
    "[introduce1] my name is $name and age :: $age"
}

fun useIntroduce1(lambda: (String, Int) -> String) = lambda("koorung", 30)

/**
 * 확장함수와 비슷하게 확장람다를 정의할 수 있다.
 * 2. String 수신객체에 (Int) -> String 을 리턴하는 확장람다 정의
 */
val introduce2: String.(Int) -> String = {"[introduce2] my name is $this and age :: $it"}

val rambda1: (String, Int) -> String = { s, n ->
    s.repeat(n) + s.repeat(n)
}

// this = 수신객체
// it = (유일한)파라미터
val rambda2: String.(Int) -> String = {
    this.repeat(it) + this.repeat(it)
}

// 두 개 이상의 파라미터를 받는 확장람다 정의
val rambda3: String.(String, Boolean) -> String = {s, b -> if(b) "$this is $s" else "$this is not $s"}

// 확장람다는 보통 함수의 파라미터로 사용되는 경우가 일반적이다.
fun fixedResult(c: String, s: String, b: Boolean, rambda: String.(String, Boolean) -> String) = c.rambda(s, b)

// 코틀린의 buildString() 함수 사용
// 자체적으로 StringBuilder 객체를 생성하고 확장람다를 생성한 객체에 적용한 후 문자열을 리턴함
fun useBuildString(vararg s: String) = buildString {
    append("[BUILD STRING] :: ")
    s.forEach { append(it) }
}

class LambdaExtension {

    @Test
    fun `람다를 파라미터로 받는 경우`() {
        val name = "koorung"
        val age = 30

        assertThat(introduce1(name, age)).isEqualTo("[introduce1] my name is koorung and age :: 30")     // 람다를 직접 전달하거나

    }

    @Test
    fun `확장람다를 정의하는 경우`() {
        assertThat("koorung".introduce2(30)).isEqualTo("[introduce2] my name is koorung and age :: 30")     // 확장람다를 사용한 경우
        assertThat(introduce2("koorung", 30)).isEqualTo("[introduce2] my name is koorung and age :: 30")    // 일반적인 함수호출로 사용해도 됨
    }

    @Test
    fun `확장람다 복습`(){
        // 일반 람다식 선언
        assertThat(rambda1("koorung", 3)).isEqualTo("koorungkoorungkoorungkoorungkoorungkoorung")

        // 확장람다 선언
        // (확장람다 선언도 일반 람다식 표현으로 사용 가능하다.)
        assertThat(rambda2("koorung", 3)).isEqualTo("koorungkoorungkoorungkoorungkoorungkoorung")
        assertThat("koorung".rambda2(3)).isEqualTo("koorungkoorungkoorungkoorungkoorungkoorung")

        assertThat("koorung".rambda3("이재학", true)).isEqualTo("koorung is 이재학")
        assertThat("koorung".rambda3("이범학", false)).isEqualTo("koorung is not 이범학")

        assertThat(fixedResult("koorung", "이재학", true, rambda3)).isEqualTo("koorung is 이재학")
        assertThat(fixedResult("koorung", "이범학", false, rambda3)).isEqualTo("koorung is not 이범학")

        // 람다를 직접 선언하여 전달할 수 있다..!
        val result = fixedResult("koorung", "이재학", true) { arg1, arg2 ->
            if (arg2) "$this 에 $arg1 를 붙여서 리턴" else "$this 에 $arg1 를 붙이지 않고 리턴"
        }
        assertThat(result).isEqualTo("koorung 에 이재학 를 붙여서 리턴")

        assertThat(useBuildString("일", "이", "삼")).isEqualTo("[BUILD STRING] :: 일이삼")
    }
}