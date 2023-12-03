package com.koorung.kotlinplayground.advancedkotlin.section2

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

// 프로퍼티로 람다를 받아서 getter에서 _value가 null일때 람다로 초기화하는 코드를 작성
class LazyInitProperty<T>(
    val init: () -> T,
): ReadWriteProperty<Any, T> {
    private var _value: T? = null
    val value: T
        get() {
            if (_value == null) {
                this._value = init()
            }
            return _value!!
        }

    // 코틀린의 by 키워드를 사용하기 위한 getValue, setValue 구현..
    override operator fun getValue(thisRef: Any, property: KProperty<*>): T = this.value
    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this._value = value
    }
}


// 이는 아래 Person3의 by lazy 키워드와 거의 동일한 구조로 사용할 수 있다...
// 이러한 패턴을 '위임' 패턴이라고 한다. (Person의 getter를 호출하면 위임클래스에 getter를 위임하여 값을 전달받아서 결과를 전달)
// 코틀린에서 by를 이용한 위임패턴을 사용하기 위해서는 operator fun인 getValue(), setValue()가 구현되어있어야 한다!
class Person2WithLazy {
//    private val delegateProperty = LazyInitProperty {
//        Thread.sleep(2_000)
//        "김기본"
//    }
//    val name: String
//        get() = delegateProperty.value

    private val delegateProperty: String by LazyInitProperty {
        Thread.sleep(2_000)
        "김기본"
    }

    val name: String = delegateProperty
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

class Person4 {
    var age: Int by Delegates.observable(20) { _, oldValue, newValue ->
        println("이전 값 :: $oldValue 새로운 값 :: $newValue")
    }

    var name: String by Delegates.vetoable("기본이름") { _, oldValue, newValue ->
        if (newValue != "") {
            println("이전 값 :: $oldValue 새로운 값 :: $newValue")
            true
        } else {
            false
        }
    }
}

fun main() {
    val p = Person4()
    p.age = 30       // 이전 값 :: 20 새로운 값 :: 30
    p.age = 40       // 이전 값 :: 30 새로운 값 :: 40
    p.age = 50       // 이전 값 :: 40 새로운 값 :: 50

    p.name = "쿠렁"   // 이전 값 :: 기본이름 새로운 값 :: 쿠렁
    p.name = ""      // (호출되지않음)
    p.name = "ㅇㅇㅇ"  // 이전 값 :: 쿠렁 새로운 값 :: ㅇㅇㅇ

    val p2 = Person5()
    print(p2.name)
}


class Person5 {
    val name: String by DelegatePersonProps("이재학")
//    val country: String by DelegateProvider("한국")
}

class DelegateProvider(
    private val initValue: String
) {
    operator fun provideDelegate(thisRef: Any, property: KProperty<*>): DelegatePersonProps {
        if(property.name != "name") {
            throw IllegalArgumentException("name만 연결 가능합니다!")
        }
        return DelegatePersonProps(initValue)
    }
}
class DelegatePersonProps(
    private val initValue: String
): ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        if(property.name != "name") {
            throw IllegalArgumentException("name만 연결 가능합니다!")
        }
        return initValue
    }
}