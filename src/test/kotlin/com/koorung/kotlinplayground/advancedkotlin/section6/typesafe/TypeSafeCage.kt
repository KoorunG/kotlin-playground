package com.koorung.kotlinplayground.advancedkotlin.section6.typesafe

import kotlin.reflect.KClass
import kotlin.reflect.cast


class TypeSafeCage {
    private val animals = mutableMapOf<KClass<*>, Animal>()

    // 여기서 KClass<T>를 타입 토큰이라고 한다.
    // 타입토큰을 파라미터로 전달하는 것으로 제네릭 없이 타입 안전한 컬렉션을 구현할 수 있다!
    fun <T: Animal> putOne(type: KClass<T>, animal: T) {
        animals[type] = type.cast(animal)
    }

    fun <T: Animal> getOne(type: KClass<T>): T = type.cast(animals[type])

    // inline reified를 활용하여 제네릭 함수 버전도 활용할 수 있다!
    inline fun <reified T: Animal> getOne(): T = this.getOne(T::class)
}