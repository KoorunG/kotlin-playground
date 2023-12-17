package com.koorung.kotlinplayground.advancedkotlin.section4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class Tests {

    @Test
    fun `직접 정의한 함수와 연산자 오버로딩을 통해 재정의한 함수 비교`(){
        var point = Point(10, -5)

        // 1. 직접 정의한 함수
        val zeroPointSymmetry = point.zeroPointSymmetry()

        // 2. 재정의한 함수
        val unaryMinus = -point // unaryMinus 재정의
        val plusOne = ++point   // inc 재정의

        println("zeroPointSymmetry ::: $zeroPointSymmetry")
        println("plusOne ::: $plusOne")

        assertThat(zeroPointSymmetry).isEqualTo(unaryMinus)
        assertThat(plusOne).isEqualTo(Point(11, -4))
    }

    @Test
    fun `확장함수도 연산자 오버로딩이 가능하다`() {
        val now = LocalDate.now()
        assertThat(now + Days(3)).isEqualTo(LocalDate.of(2023, 12, 20))
        // Int.day 를 확장프로퍼티로 정의하여 사용
        assertThat(now + 3.day).isEqualTo(LocalDate.of(2023, 12, 20))
    }

    @Test
    fun `mutableList에 += 적용하기`(){
        val list = mutableListOf("A", "B", "C")
        // mutableList에 plusAssign이 정의되어있음
        list += "D"
        assertThat(list).isEqualTo(mutableListOf("A", "B", "C", "D"))
    }

    @Test
    fun `불변 list에 += 적용하기`(){
        var list = listOf("A", "B", "C")
        /**
         * 불변 list에는 plusAssign이 정의되어있지 않지만, plus만 정의되어 있기 때문에...
         * 일단 list.plus()를 호출하고 list = list.plus(...) 으로 교체한다.
         * 따라서 var인 경우에만 이 동작이 가능하다. (val인 경우 컴파일에러)
         */
        list += "D"
        assertThat(list).isEqualTo(listOf("A", "B", "C", "D"))
    }

    /**
     * List, Map의 편의기능 또한 하나의 연산이며, (list[3]) (map[2] = "value")
     * 함수호출도 연산이다! (operator fun invoke)
     */
    @Test
    fun `함수의 invoke 연산자 오버로딩하여 호출하기`() {
        assertThat(Operator.PLUS.invoke(3, 4)).isEqualTo(7)
        assertThat(Operator.PLUS(3, 4)).isEqualTo(7)        // calc를 노출하지 않고 Enum 자체를 바로 함수로 사용할 수 있게 된다..!
        assertThat(Operator.MULTIPLY(3, 4)).isEqualTo(12)        // calc를 노출하지 않고 Enum 자체를 바로 함수로 사용할 수 있게 된다..!
    }
}