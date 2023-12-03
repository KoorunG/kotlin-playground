package example

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.random.nextLong

data class Fruit(
    val name: String,
    val price: Long,
) {
    companion object {
        fun random(): Fruit {
            val names = listOf("사과", "바나나", "수박", "딸기", "파인애플")
            val randomInt = Random.nextInt(0, 5)
            val random = Random.nextLong(1_000L..100_000L)
            return Fruit(names[randomInt], random)
        }
    }
}

/**
 * jmh 벤치마크 테스트 설정
 * 벤치마크 클래스는 src/jmh/kotlin 하위 패키지에 존재해야 하며
 * open class여야 한다.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class SequencesTest {
    private val fruits = mutableListOf<Fruit>()

    @Setup
    fun init() {
        repeat(2_000_000) { fruits.add(Fruit.random()) }
    }

    @Benchmark
    fun kotlinSequence() {
        fruits.asSequence()
            .filter { it.name == "사과" }
            .map { it.price }
            .take(10_000)
            .average()
    }

    @Benchmark
    fun kotlinIterator() {
        fruits.filter { it.name == "사과" }
            .map { it.price }
            .take(10_000)
            .average()
    }
}