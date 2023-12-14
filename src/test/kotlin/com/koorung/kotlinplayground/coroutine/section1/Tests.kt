package com.koorung.kotlinplayground.coroutine.section1

import kotlinx.coroutines.*
import org.assertj.core.api.Assertions
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

    @Test
    fun `코루틴을 취소하는 방법1 - suspend 함수를 사용한다`(){
        runBlocking {
            val job1 = launch {
                // delay나 yield는 suspend 함수이므로 코루틴의 취소에 협조할 수 있다.
//                delay(1_000L)   // 주석처리하면 job1은 취소되지 않음
                printWithThread("JOB 1")
            }
            val job2 = launch {
                delay(1_000L)
                printWithThread("JOB 2")
            }

            delay(100)
            job1.cancel()
            job2.cancel()
        }
    }

    @Test
    fun `코루틴을 취소하는 방법2 - 취소 요청을 받으면 CancellationException을 던진다`(){
        runBlocking {
            // 1. Default라는 다른 쓰레드에서 job을 실행
            printWithThread("job을 실행합니다..")
            val job = launch(Dispatchers.Default) {
                var i = 1
                var nextPrintTime = System.currentTimeMillis()
                while (i <= 5) {
                    if (nextPrintTime <= System.currentTimeMillis()) {
                        printWithThread("${i++} 번째 출력!")
                        nextPrintTime += 500L
                    }

                    // CoroutineScope.isActive 프로퍼티로 코루틴은 스스로의 상태를 확인할 수 있다!
                    // 취소요청을 받았다면 "isActive == false"
                    // 비활성화 상태라면 CancellationException 예외 던지기!
                    if (!isActive) throw CancellationException()
                }
            }

            delay(100)

            // main (여기서는 testworker) 쓰레드에서 예외를 감지하여 job을 취소시킨다
            job.cancel()
        }
    }

    @Test
    fun `delay나 yield 함수도 내부적으로 CancellationException으로 취소에 협력한다`(){
        runBlocking {
            val job = launch {
                try {
                    delay(1000L)
                } catch (e: CancellationException) {
                    // 예외를 먹어버리면
                }
                printWithThread("취소되지 않았음...")
            }
            delay(100)
            printWithThread("취소 시작!")
            job.cancel()
        }
    }

    @Test
    fun `launch를 root 코루틴으로 만들기`() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            printWithThread("Default thread :: launch")
        }

        delay(timeMillis = 1_000L)
    }

    @Test
    fun `launch 블록은 블록의 결과를 바로 출력한다`(){
        runBlocking {
            CoroutineScope(Dispatchers.Default).launch {
//                throw IllegalArgumentException()
            }

            delay(timeMillis = 1_000L)
        }
    }

    @Test
    fun `async 블록은 결과를 바로 출력하지 않는다`(){
        runBlocking {
            val async = CoroutineScope(Dispatchers.Default).async {
                throw IllegalArgumentException()
            }

            delay(timeMillis = 1_000L)
            // .await() 으로 호출되어야 예외가 발생한다.
//            async.await()
        }
    }

    @Test
    fun `async 블록은 블록의 결과를 일단 deferred 객체에 담아둔다`(){
        runBlocking {
            // 그러나 SupervisorJob()으로 등록하면 전파되지 않는다
            val async = async(SupervisorJob()) {
                throw IllegalArgumentException()
            }

            delay(timeMillis = 1_000L)

            // 역시 .await() 으로 호출하면 예외가 발생한다.
//            async.await()
        }
    }

    @Test
    fun `자식 코루틴의 예외는 CancellationException을 제외하고 기본적으로 부모에게 전파된다`(){
        runBlocking {
            launch {
                throw CancellationException()   // 정상동작
//                throw IllegalArgumentException() // 에러가 발생함
            }

            delay(timeMillis = 100L)
        }
    }

    @Test
    fun `CoroutineExceptionHandler으로 예외가 발생한 이후 예외를 다룰 수 있다`(){
        runBlocking {
            // 2. 예외가 발생한 이후 로깅처리
            val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
                printWithThread("예외처리")
                // 첫번째 파라미터: 핸들러가 처리되는 context
                printWithThread("context ::: $context")
                // 두번째 파라미터: 예외
//                throw throwable
            }

            // 1. CoroutineExceptionHandler을 context로 등록한다
            CoroutineScope(Dispatchers.Default).launch(context = exceptionHandler) {
                throw IllegalArgumentException()
            }

            delay(100L)
        }
    }
}