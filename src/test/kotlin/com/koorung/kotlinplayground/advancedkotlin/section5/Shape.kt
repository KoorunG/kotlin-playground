package com.koorung.kotlinplayground.advancedkotlin.section5

/**
 * @Retention: 어노테이션이 유지되는 방식을 제어함
 * 1. SOURCE: 어노테이션이 컴파일타임에만 존재
 * 2. BINARY: 어노테이션이 런타임까지 존재, 단 리플랙션을 쓸 수 없음
 * 3. RUNTIME: 어노테이션이 런타임까지 존재, 리플랙션O (디폴트값)
 *
 * 보통은 RUNTIME으로 쓴다...
 *
 * @Target: 어노테이션이 붙을 수 있는 위치를 정의함, n개 설정 가능
 *
 * @Repeatable: 이 어노테이션을 붙이면 반복하여 사용 가능!
 */
//@Retention(AnnotationRetention.RUNTIME)
//@Target(CLASS, ANNOTATION_CLASS, FIELD)
//@Repeatable
annotation class Shape(
    val name: String,
//    val number: Int,
//    val clazz: KClass<*>,   // KClass: 코틀린의 리플랙션의 일종
) {

}

// 코틀린은 언어적 특성으로 인해 어노테이션이 어디 붙을 지 애매하게 보이는 경우가 있다.
// 이를 use-site target 이라고 한다.
// 주로 field, get, set 등이 사용됨...
// 암시적으로 param > property > field 순서...
@Shape(name = "클래스")
class Rectangle (@param:Shape(name = "직사각형") val name: String)