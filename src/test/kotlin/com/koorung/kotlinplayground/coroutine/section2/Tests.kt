package com.koorung.kotlinplayground.coroutine.section2

import com.koorung.kotlinplayground.coroutine.section1.printWithThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Tests {

    @Test
    fun `부모 코루틴은 성공했으나 자식 코루틴중 하나가 예외를 던지는 경우`() {
        assertThrows<IllegalStateException> {
            runBlocking {
                launch {
                    delay(600L)
                    printWithThread("A")
                }

                // 예외가 COMPLETING 상태인 부모로 전파되고 다른 자식 코루틴에게도 취소 요청을 보낸다.
                // Structured Concurrency: 부모, 자식 코루틴이 유기적으로 동작 -> 코드내의 예외가 유실되지 않고 적절히 처리된다!
                launch {
                    delay(500L)
                    throw IllegalStateException("코루틴 실패!")
                }
            }
        }
    }

    // 단 CancellationException의 경우 '정상취소'로 간주하므로 예외가 전파되지 않는다.
    @Test
    fun `자식 코루틴은 성공했으나 부모 코루틴이 예외를 던지는 경우`() {
        val localizedMessage = assertThrows<IllegalStateException> {
            runBlocking {
                launch {
                    delay(300L)
                    printWithThread("자식 코루틴A 성공")
                }

                launch {
                    delay(400L)
                    printWithThread("자식 코루틴B 성공")
                }

                throw IllegalStateException("부모 코루틴 실패")
            }
        }.localizedMessage

        assertThat(localizedMessage).isEqualTo("부모 코루틴 실패")
    }
}