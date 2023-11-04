package com.koorung.kotlinplayground.advancedkotlin.section1

/* 제네릭 활용법.. */

/**
 * 두 List에 겹치는 원소가 하나라도 있는지 검사하는 확장함수
 */
internal fun <T: Any> List<T>.hasIntersection(other: List<T>): Boolean =
    (this.toSet() intersect other.toSet()).isNotEmpty()


/**
 * 수신객체의 타입정보와 toString()을 리턴하는 확장함수
 *
 * 1. 제네릭 파라미터에 reified 키워드를 붙인다.
 * 2. 함수를 inline으로 만들어준다
 */
internal inline fun <reified T> T.toSuperString() = "${T::class.simpleName}: ${this.toString()}"

/**
 * List<*> 타입의 수신객체가 <T> 타입을 하나라도 가지고 있는지를 리턴하는 확장함수
 *
 * 1. <reified T> 로 T 타입정보 사용 가능
 * 2. 수신객체를 List<*>로 정의하여 모든 List 타입에 대해 사용가능
 * (단 이 T를 이용하여 인스턴스를 생성하거나 companion object에 접근하는 것은 불가능하다. 어디까지나 리플랙션 정보만 활용가능..!)
 */
internal inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean = this.any { it is T }