package com.koorung.kotlinplayground.atomickotlin.atom80

import com.koorung.kotlinplayground.atomickotlin.atom80.KotlinScope.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

// 코틀린의 영역함수

/**
 * 1. it으로 접근하는 경우 (람다의 파라미터에 이름을 붙일 수 있다.)
 * let : 문맥을 it으로 접근, 마지막 식을 리턴
 * also : 문맥을 it으로 접근, 수신객체를 리턴
 *
 * 2. this로 접근하는 경우 (코드를 깔끔하게 유지할 수 있다.)
 * apply : 문맥을 this로 접근, 수신객체를 리턴
 * run : 문맥을 this로 접근, 마지막 식을 리턴
 *
 * 3. 일반함수인 경우 (run과 동일하나, 일반함수이다)
 * with : 문맥을 this로 접근, 마지막 식을 리턴, "확장함수가 아닌 일반함수"
 *
 * 4. 람다의 결과를 만들어 리턴해야 할 경우 let, run을 사용한다.
 * 5. 식을 연쇄적으로 사용하여 변경한 수신객체를 리턴해야 할 경우 apply, also를 사용한다.
 * 6. ?. 나 ?: 같은 null 처리 기법을 같이 사용할 경우 매우 유용하다.
 */

data class Human(
    val name: String,
    var age: Int? = null
) {
    fun incrementAge() =
//        run {
//        var notNullAge =
//            requireNotNull(age) {
//                "나이가 null이면 동작하지 않습니다."
//            }
//        notNullAge++
//        this.age = notNullAge
//        this.age
//    }
        this.age?.plus(1)   // age가 null이 아닐경우에만 동작
}

fun gets(): String? = if (Random.nextBoolean()) "str!" else null

enum class KotlinScope {
    LET,
    RUN,
    APPLY,
    ALSO;

    // 영역함수는 '가독성'이 우선이므로 영역함수를 내포하는 방식으로 코딩하는 것은 자제하도록 한다. (또는 this를 명시적으로 선언하거나)
    // 보통 람다를 인자로 전달하면 람다코드를 외부객체에 넣기 때문에 일반 호출에 비해 런타임의 부가비용이 더 발생함.
    // 코틀린의 inline 함수로 선언할 경우 모든 런타임의 부가비용을 줄일 수 있음.. (함수 호출 -> 함수 본문 으로 치환)
    // 함수 전체 비용에서 함수 호출 비용의 비중이 클 경우 효과가 좋다. (즉 작은 함수일 경우 inline의 효과가 좋다)
    inline fun execute(str: String?, block: (String?) -> String?) = when(this) {
        LET -> block(str)?.let { "$it is in $this" }?: "[$this] 파라미터가 비어있습니다."
        RUN -> block(str)?.run { "$this is in ${this@KotlinScope}" }?: "[$this] 파라미터가 비어있습니다."
        APPLY -> {
            var result = "[$this] 파라미터가 비어있습니다."
            block(str)?.apply {
                result = "$this is in ${this@KotlinScope}"
            }
            result
        }
        ALSO -> {
            var result = "[$this] 파라미터가 비어있습니다."
            block(str)?.also {
                result = "$it is in ${this@KotlinScope}"
            }
            result
        }
    }

}

class ScopeFunctions {

    @Test
    fun `영역함수 let을 safety call과 같이 사용`() {
        println(gets()?.let {
            "${it.removeSuffix("!")}${it.length}"
        })
    }

    @Test
    fun `영역함수는 연쇄호출에서 Nullable 타입과 같이 쓸 수 있다`() {

        val block: (String?) -> String? = { it?.takeUnless { it.isBlank() } }

        listOf(LET, RUN, APPLY, ALSO).forEach {
            println(it.execute("koorung", block))
        }

        listOf(LET, RUN, APPLY, ALSO).forEach {
            println(it.execute("  ", block))
        }
    }

    @Test
    fun `apply는 this로 접근하여 수신객체를 리턴한다`() {
        val h1 = Human("h1")
        val h2 = Human("h2", 20)
        val h3 = Human("h3")
        val h4 = Human("h4", 30)

//        assertThat(h1.incrementAge()).isNull()

        var count = 0
        listOf(h1, h2, h3, h4).forEach {
            it.incrementAge()?.apply {
                ++count
                println("실행 횟수 ::: $count, 증가한 나이 ::: $this")
            }
        }
    }

}