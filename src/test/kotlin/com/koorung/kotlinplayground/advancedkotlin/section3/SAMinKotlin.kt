package com.koorung.kotlinplayground.advancedkotlin.section3

// Kotlin에서 SAM interface를 만들 때 `fun interface` 키워드를 사용한다..!
fun interface StringFilter {
    fun predicate(): Unit
}

fun useRef() {
}

class RefClass(
    private var _name: String
) {
    val name = _name
}

fun RefClass.printName() {
    println(this.name)
}

fun main() {
    /**
     * - Kotlin에서 SAM 인터페이스를 호출하는 방법
     * "인터페이스명 { }"
     * (어차피 코틀린에서 함수는 일급시민이기 때문에 사용할 일이 거의 없다, 자바의 SAM 인터페이스를 사용할 때만 알면 된다.)
     */
    StringFilter { println("Hello world") }

    /**
     * - Kotlin에서 래퍼런스 사용
     * ::을 사용하여 래퍼런스를 얻을 수 있다.
     * Java와 달리 KXXX와 같은 리플랙션 객체가 리턴된다. (Java는 함수형 인터페이스가 리턴됨)
     */

    // 1. 메소드 래퍼런스 = "::함수명"
    val ref = ::useRef

    // 2. 클래스의 생성자 래퍼런스 = "::클래스명"
    val ref2 = ::RefClass

    // 3. 클래스의 프로퍼티 래퍼런스 = "클래스명::프로퍼티명"
    val ref3 = RefClass::name.getter

    // 4. 인스턴스의 프로퍼티 래퍼런스 = "인스턴스::프로퍼티명"
    val r = RefClass("테스트")
    val ref4 = r::name.getter

    // 5. 확장함수의 래퍼런스를 얻을 수도 있다.
    val ref5 = RefClass::printName
}