package com.koorung.kotlinplayground.advancedkotlin.section2

class Person {
    // 코틀린에서는 lateinit 키워드로 인스턴스화 시점과 초기화 시점을 분리할 수 있다!
    // 내부적으로 컴파일 단계에서 nullable 변수로 바꾸고, 인스턴스화 시점에서 null일 경우 예외를 던지는 방식으로 구현되어있다.
    // 따라서 primitive type에서 사용할 수 없다.
    lateinit var name: String

    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = this.name[0] + (1 until this.name.length).joinToString("") { "*" }
}

// 1. backing property를 이용하는 경우
class Person2 {
    private var _name: String? = null
    val name: String
        // null일 경우 비용이 비싼 초기화 로직을 타고
        // null이 아닐 경우 바로 인스턴스화 한다.
        get() = if (null == _name) defaultName else this._name!!

    private val defaultName: String
        get() {
            Thread.sleep(2_000)
            return "김기본"
        }
}

// 2. by lazy 키워드를 사용하는 경우
// 프로퍼티의 getter 이 최초 호출될 때 람다가 실행된다.
// Thread-safe 하다.
class Person3 {
    val name: String by lazy {
        Thread.sleep(2_000)
        "김기본"
    }

    val age: Int = 0
}