package com.koorung.kotlinplayground.coroutine.review

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

class MainTest {
    @Test
    fun main() = runBlocking {
        printWithThread("START")
        launch { newRoutine() }
        yield()
        printWithThread("END")
    }

    @Test
    fun `runBlocking 테스트`() {
        printWithThread("[MAIN] START")
        runBlocking {
            printWithThread("[RUN-BLOCKING] START")
            launch {
                printWithThread("[LAUNCH] START")
                delay(2_000L)
                printWithThread("[LAUNCH] END")
            }
            printWithThread("[RUN-BLOCKING] END")
        }
        printWithThread("[MAIN] END")
    }

    @Test
    fun `launch 테스트`() {
        runBlocking {
            val job = launch(start = CoroutineStart.LAZY) { printWithThread("Hello, launch") }
            delay(1_000L)
            job.start()
        }
    }

    @Test
    fun `launch 취소 테스트`() {
        runBlocking {
            val job = launch {
                repeat(5) {
                    printWithThread(it)
                    delay(500L)
                }
            }
            delay(1_000L)
            job.cancel()
        }
    }

    @Test
    fun `launch 대기 테스트`() {
        runBlocking {
            val job1 = launch {
                delay(1_000L)
                printWithThread("JOB 1")
            }
            job1.join()
            val job2 = launch {
                delay(1_000L)
                printWithThread("JOB 2")
            }
        }
    }

    @Test
    fun `async 테스트`() {
        val totalTime = measureTimeMillis {
            runBlocking {
                val job1 = async {
                    delay(2_000L)
                    3 + 5
                }
                val job2 = async {
                    delay(500L)
                    job1.await() + 5 + 8
                }
                printWithThread("result ::: ${job1.await() + job2.await()}")
            }
        }
        printWithThread("totalTime ::: $totalTime")
    }

    @Test
    fun `코루틴이 취소에 협력하는 방법 테스트`() {
        printWithThread("메인 쓰레드 시작")
        runBlocking {
            val job = launch(Dispatchers.Default) {// 1. launch를 Default 쓰레드에서 실행
                printWithThread("Default 쓰레드에서 launch 시작")
                var i = 1
                var nextPrintTime = System.currentTimeMillis()
                while (i <= 5) {
                    if (nextPrintTime <= System.currentTimeMillis()) {
                        printWithThread("${i++} 번째 출력!")
                        nextPrintTime += 1_000L
                    }
                    if (!isActive) throw CancellationException()
                }
            }
            delay(100L) // 2. delay() suspend 함수 호출
            job.cancel()
            /**
             * 1, 2 조건이 만족할 경우 runBlocking 코루틴에서 cancel()로 신호를 보내면
             * launch가 실행되고 있는 쓰레드에 isActive = false가 전달되고
             * 이를 받아서 CancelationException을 던지면
             * launch 블록이 취소된다.
             */
        }
    }

    @Test
    fun `루트 코루틴을 만드는 방법`() {
        runBlocking {
            // 1. launch : 루트인 경우 예외가 발생하면 바로 예외를 던지고 코루틴 종료
            CoroutineScope(Dispatchers.Default).launch {
                delay(100L)
                printWithThread("job 1")
            }
            // 2. async : 루트인 경우 await()이 호출되지 않는다면 예외를 던지지 않음
            CoroutineScope(Dispatchers.Default).async {
                delay(100L)
                printWithThread("job 2")
            }
        }
    }

    @Test
    fun `CoroutineExceptionHandler으로 예외를 처리하는 방법`() {
        runBlocking {
            val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
                printWithThread("예외")
//                throw throwable
            }

            val job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
                throw IllegalStateException()
            }
            delay(1_000L)
        }

        @Test
        fun `CoroutineScope를 직접 선언하면 runBlocking을 선언하지 않아도 된다`() {
            CoroutineScope(Dispatchers.Default).launch {
                delay(500L)
                printWithThread("job1")
            }

            // 이 경우 runBlocking의 쓰레드를 블록하는 기능이 없기 때문에 직접 쓰레드를 제어해준다.
            Thread.sleep(1_000L)
        }

        class AsyncLogic {
            private val scope = CoroutineScope(
                CoroutineName("AsyncLogic 코루틴") +
                        Dispatchers.Default +
                        SupervisorJob() +
                        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
                // ...
            )

            fun doSomething() {
                scope.launch {
                    // 무언가 코루틴이 시작되어 작업 실행
                }
            }

            fun destory() {
                scope.cancel()
            }
        }
    }

    @Test
    fun `suspend 함수를 활용하여 다른 비동기 라이브러리와 호환성을 유지할 수 있다`() {
        runBlocking {
            val result1 = call1()
            val result2 = call2(result1)
            printWithThread("result2 :::: $result2")
        }
    }

    suspend fun call1() =
        CoroutineScope(Dispatchers.Default).async {
            Thread.sleep(1_000L)
            100
        }.await()

    suspend fun call2(num: Int): Int {
        return CompletableFuture.supplyAsync {
            Thread.sleep(1_000L)
            num * 3
        }.get()
    }
}

suspend fun newRoutine() {
    val num1 = 1
    val num2 = 2
    yield()
    printWithThread("${num1 + num2}")
}

fun printWithThread(str: Any) {
    println("[${Thread.currentThread().name}] :: $str")
}