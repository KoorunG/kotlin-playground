package com.koorung.kotlinplayground.atomickotlin.atom51

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Sequence {

    @Test
    fun `코틀린의 시퀀스는 이터레이션만 가능하고 인덱싱은 불가능하다()`() {
        // constrainOnce() 로 한번만 이터레이션 하는 것을 보장할 수 있다
        // (시퀀스는 원래 한번만 이터레이션 가능하나 일부 시퀀스에 대해 반복 이터레이션이 가능한 경우가 있는데 좋지 않음)
        var sequence = List(10) { it }.asSequence().constrainOnce()

//        sequence.get(0) // 컴파일에러
//        sequence[0]     // 컴파일에러
        assertThat(sequence.toList()[0]).isEqualTo(0)   // 1. 여기서 시퀀스가 한번 Consume 됐기 때문에

//        val sum = sequence
//            .map { it + 1 }
//            .filter { it <= 5 }
//            .distinctBy { it }
//            .sum()

        // 2. 재할당해줘야 에러가 발생하지 않는다. (웬만하면 변수에 할당해서 쓰지 말고 바로 소비하자)
        sequence = List(10) { it }.asSequence().constrainOnce()
        val sum = sequence
            .map { it + 1 }
            .filter { it <= 5 }
            .distinctBy { it }
            .sum()

        assertThat(sum).isEqualTo(15)
    }

    @Test
    fun `컬렉션은 수평적으로 평가되나, 시퀀스는 수직적으로 평가된다`() {
        val list = List(4) { it + 1 }

        /**
         * list ::: 1
         * list ::: 2
         * list ::: 3
         * list ::: 4
         */
        list
            .filter {
                println("list ::: $it")
                it % 2 == 0 }
            .map { it * it }
            .any { it < 10 }

        /**
         * sequence ::: 1
         * sequence ::: 2
         */
        list.asSequence()
            .filter {
                println("sequence ::: $it")
                it % 2 == 0 }
            .map { it * it }
            .any { it < 10 }    // 최종값이 predicate를 만족하는 값을 만나는 시점에 종료

    }

    @Test
    fun `generateSequence() 사용법`() {

        // generateSequence : 무한한 시퀀스를 생성하기 때문에 0부터 숫자를 무한히 출력한다.
//        for(s in generateSequence(0) { it + 1 }) println(s)

        // 람다만 인자로 받는 generateSequence() 함수도 존재한다.
        // 람다는 더 이상 원소가 없을 경우 null을 반환하며 이 때 시퀀스는 종료된다.
        val numbers = MutableList(10) { it + 1 }

        assertThat(generateSequence {
            println(numbers)
            numbers.removeAt(0).takeUnless { it > 5 }
        }.toList()).hasSize(5)

    }
}