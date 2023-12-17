package com.koorung.kotlinplayground.advancedkotlin.section4

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// DSL 작성에 필요한 함수를 모아놓은 File

// @DslMarker 애노테이션은 annotation class 위에 붙일 수 있으며
// 이 어노테이션이 붙은 클래스는 this의 생략은 가장 가까운 수신객체에 대해서만 할 수 있게 된다...!
// (즉 DSL 사용 시 service 블록 안에서 다시 service를 무작정 호출하는 경우를 막을 수 있다.)
@DslMarker
annotation class YamlDsl

// ReadWriteProperty를 상속한 object를 리턴하는 함수
// DSL에서 version { ... } 이 호출되지 않거나, 두 번 이상 호출된 경우 예외를 던짐
fun <T> onceNotNull() = object: ReadWriteProperty<Any?, T> {
    private var _value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (this._value == null) {
            throw IllegalArgumentException("변수가 아직 초기화되지 않았습니다. ::: ${property.name}")
        }
        return this._value!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(null != this._value) {
            throw IllegalArgumentException("이 변수는 값을 한 번만 초기화 할 수 있습니다. ::: ${property.name}")
        }
        this._value = value
    }
}

// YAML형식의 indent를 지원하기 위한 함수
fun StringBuilder.appendNew(str: String, indent: String = "", times: Int = 0) {
    (1..times).forEach { _ -> this.append(indent)}
    this.append(str)
    this.append("\n")
}

fun String.addIndent(indent: String, times: Int = 0): String {
    // joinToString = list 내의 원소를 람다식의 결과로 바꾼 뒤 합치는 함수
    // ex) appendNew.addIndent("  ", 2) -> appendNew 문자열이 "  " 인덴트가 두 번 적용된 것으로 교체된다!
    val allIndent = (1..times).joinToString("") { indent }
    return this.split("\n").joinToString("\n") { "$allIndent$it" }
}

// 연산자 오버로딩으로 환경변수의 env("key" - "value")를 구현
operator fun String.minus(other: String) = Environment(key = this, value = other)