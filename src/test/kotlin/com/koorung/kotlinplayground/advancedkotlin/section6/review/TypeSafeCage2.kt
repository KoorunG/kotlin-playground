package com.koorung.kotlinplayground.advancedkotlin.section6.review

import com.koorung.kotlinplayground.advancedkotlin.section6.typesafe.GoldFish
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.reflect.cast

class TypeSafeCage2 {
    private val animals: MutableMap<KClass<*>, Animal> = mutableMapOf()

    // KClass<T> 를 타입토큰이라고 한다!
    fun <T : Animal> putOne(type: KClass<T>, animal: T) {
        animals[type] = type.cast(animal)
    }

    fun <T : Animal> getOne(type: KClass<T>): T = type.cast(animals[type])

    // 제네릭 버전!
    // 내부적으로 T를 통해 내부적으로 타입토큰을 사용하는 함수를 호출함
    // '타입 안전 이종 컨테이너' 라고 한다!
    inline fun <reified T : Animal> getOne() = this.getOne(T::class)
}

// 상속용으로만 사용할 것이므로 추상클래스로 선언하여 객체화를 막는다.
// 원래 제네릭으로 넘긴 T 정보는 런타임에 소거되지만 이렇게 하면 T 정보를 기억할 수 있다!
// jackson 라이브러리의 TypeReference<T> 가 이에 해당한다! (많이 봄)
abstract class SuperTypeToken<T> {
    private val subtypeToken = this::class.supertypes[0] // 이 클래스를 상속받은 타입정보를 가져온다. (일단 하나만)
    private val subTypeGenerics = subtypeToken.arguments[0] // 일단 하나만
    val subType = subTypeGenerics.type!! // not null 단언!

    // auto generation으로 구현
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SuperTypeToken<*>) return false
        if (subType != other.subType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = subtypeToken.hashCode()
        result = 31 * result + subTypeGenerics.hashCode()
        result = 31 * result + subType.hashCode()
        return result
    }


}

class TypeTokenTest {

    @Test
    fun `타입안전 이종 컨테이너 테스트`(){
        val typeSafeCage2 = TypeSafeCage2()
        val animal = Carp("잉어")
        typeSafeCage2.putOne(Carp::class, animal)
        val one = typeSafeCage2.getOne(Carp::class)
        val two = typeSafeCage2.getOne<Carp>()
        println("isEqualTo() ::::: ${animal.name == one.name}")
        assertThat(animal).isExactlyInstanceOf(Carp::class.java)
        assertThat(one).isExactlyInstanceOf(Carp::class.java)
        assertThat(two).isExactlyInstanceOf(Carp::class.java)
    }

    @Test
    fun `직접 구현한 SuperTypeToken 테스트`(){
        val superTypeToken1 = object: SuperTypeToken<List<GoldFish>>(){}
        val superTypeToken2 = object: SuperTypeToken<List<GoldFish>>(){}
        val superTypeToken3 = object: SuperTypeToken<List<Carp>>(){}

        assertThat(superTypeToken1).isEqualTo(superTypeToken2)
        assertThat(superTypeToken2).isNotEqualTo(superTypeToken3)
    }
}


