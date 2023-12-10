package com.koorung.kotlinplayground.coroutine.section1

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

/**
 * suspend 함수
 * : 이 키워드가 붙어야 다른 suspend 함수를 호출할 수 있다.
 * : (yield가 대표적인 내장 suspend 함수)
 */
suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    /**
     * yield 함수
     * : 현재 코루틴을 중지하고 다른 코루틴이 실행되도록 한다.
     */
    yield()
    printWithThread("${num1 + num2}")
}

class CoroutineTests {

    /**
     * runBlocking 블록
     * : 일반루틴 세계와 코루틴 세계를 연결
     * : 블록안의 내용으로 새로운 코루틴을 생성한다.
     */
    @Test
    fun `코루틴의 runBlocking 블록 사용`() {
        runBlocking {
            printWithThread("START")
            /**
             * launch 블록
             * : 반환값이 없는 코루틴을 생성한다.
             */
            launch {
                newRoutine()
            }
            yield()
            printWithThread("END")
        }
    }

    @Test
    fun `runBlocking은 코루틴을 완료하기 전까지 쓰레드를 blocking 한다`() {
        // 때문에 runBlocking은 코루틴을 init 하는 용도 이외에는 거의 사용하지 않는다.
        runBlocking {
            printWithThread("START")
            launch {
                delay(3_000L)
                printWithThread("LAUNCH END")
            }
        }
        printWithThread("END")
    }

    @Test
    fun `launch는 리턴값이 없는 코드를 시행할 때 사용한다`() {
        // launch는 리턴값이 아니라 launch로 생성된 코루틴을 제어할 수 있는 job 객체이다.
        runBlocking {
            // start 파라미터로 launch의 시작시점을 제어할 수 있다 (디폴트 = 생성과 동시에 실행)
            val job = launch(start = CoroutineStart.LAZY) {
                printWithThread("LAUNCH START")
            }
            // 명시적으로 start()를 호출하여 코루틴을 실행시킨다.
            delay(2_000L)
            job.start()
        }
    }

    @Test
    fun `launch로 생성된 job 객체 취소`() {
        runBlocking {
            val job = launch {
                (1..5).forEach {
                    printWithThread(it)
                    delay(500)
                }
            }

            delay(1_000L)
            // cancel()로 job을 종료
            job.cancel()
        }
    }

    @Test
    fun `launch로 생성된 job 객체 대기`() {
        runBlocking {
            val job1 = launch {
                delay(1_000L)
                printWithThread("JOB 1")
            }
            // join()으로 job1이 끝날때 까지 대기했다가 다음 코루틴 실행
            job1.join()

            val job2 = launch {
                delay(1_000L)
                printWithThread("JOB 2")
            }
        }
    }

    @Test
    fun `async는 launch와 다르게 실행결과를 반환할 수 있다`() {
        runBlocking {
            // Job을 상속받은 Deferred 객체를 리턴
            val deferredJob= async {
                delay(3_000L)
                3 + 5
            }
            // await()으로 job의 결과를 리턴받을 수 있다.
            println("await :::: ${deferredJob.await()}")
        }
    }

    @Test
    fun `api에서 await을 활용하는 예제 테스트`() {
        runBlocking {
            val measureTimeMillis = measureTimeMillis {
                val result = async {
                    apiCall1(3)
                }

                val result2 = async {
                    apiCall2(4)
                }
                println("result ::: ${result.await() + result2.await()}")
            }
            println("measureTimeMills ::: $measureTimeMillis ms")
        }
    }
}