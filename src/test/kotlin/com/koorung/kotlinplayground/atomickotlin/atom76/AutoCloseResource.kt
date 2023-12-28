package com.koorung.kotlinplayground.atomickotlin.atom76

import org.junit.jupiter.api.Test

/**
 * 자바의 try with resource 구문과 같이
 * 코틀린에서도 AutoClosuable 인터페이스를 통한 자원 자동 해제를 지원하는 문법이 있다.
 * use는 람다를 파라미터로 받으며, 람다안의 코드를 실행 후 자동으로 자원을 정리해준다. (close()를 자동호출한다)
 */

class TestWithResource: AutoCloseable {
    override fun close() {
        println("자원정리")
    }
}
class AutoCloseResource {

    @Test
    fun `use를 이용한 자원정리 테스트`() {
        TestWithResource().use {
            println("어떤 행동을 함...")
            println("어떤 행동을 함...")
            println("어떤 행동을 함...")
            // 마지막에 close() 호출
        }
    }
}