package com.koorung.kotlinplayground.atomickotlin.atom65

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


interface Languages {
    fun name(): String
    fun utility(): Long
    fun isFlexible(): Boolean
}

interface Frameworks {
    fun frameworkName(): String
    fun verbose(): Long
}

class Javaa : Languages {
    override fun name(): String = "Java"

    override fun utility(): Long = 10L

    override fun isFlexible(): Boolean = false

    fun javaUtility(u: String) = u
}

class Springg : Frameworks {
    override fun frameworkName(): String = "Springg"
    override fun verbose(): Long = 15L
}

// 인터페이스 by 위임객체
class Kotlinn(
    private val javaa: Javaa = Javaa(),
    private val springg: Springg = Springg()
) : Languages by javaa,
    Frameworks by springg { // 클래스위임을 사용하면 다중상속처럼 동작하도록 구현하는 것도 가능하다.. (인터페이스 개수만큼 가능!)

    override fun name(): String = "Kotlin"

    override fun utility(): Long = 20L

    override fun isFlexible(): Boolean = true

    override fun frameworkName(): String = "Ktorr"

    override fun verbose(): Long = 0L

    // javaUtility는 인터페이스 명시된 메소드가 아니기 때문에 별도로 위임해야 한다.
    fun kotlinnUtillity(k: String) = javaa.javaUtility(k) + " by Kotlin"
}

/**
 * - 클래스 위임
 * 합성과 상속의 중간지점..
 * (합성처럼 클래스의 멤버로 인스턴스를 가져오면서
 *  상속처럼 인터페이스를 외부로 노출한다. 또한 하위타입으로 업캐스트할 수 있다.)
 *
 *  코틀린에서는 클래스 위임을 간단하게 쓸 수 있는 전용 문법을 제공한다. (by)
 */
class Delegation {

    @Test
    fun `클래스 위임 테스트`() {
        val javaa = Javaa()
        val kotlinn = Kotlinn()

        assertThat(javaa.name()).isEqualTo("Java")
        assertThat(kotlinn.name()).isEqualTo("Kotlin")
    }

    @Test
    fun `인터페이스에 명시된 메소드만 위임할 수 있다`() {
        val javaa = Javaa()
        val kotlinn = Kotlinn()
        val u = "test"

        assertThat(javaa.javaUtility(u)).isEqualTo("test")
        assertThat(kotlinn.kotlinnUtillity(u)).isEqualTo("test by Kotlin")
    }

    @Test
    fun `인터페이스 개수만큼 클래스 위임이 가능하다`(){
        val kotlinn = Kotlinn()

        assertThat(kotlinn.frameworkName()).isEqualTo("Ktorr")
        assertThat(kotlinn.verbose()).isEqualTo(0L)
    }
}