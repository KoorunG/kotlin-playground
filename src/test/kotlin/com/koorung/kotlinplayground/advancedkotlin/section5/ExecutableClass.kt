package com.koorung.kotlinplayground.advancedkotlin.section5

import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberFunctions

@Executable
class ExecutableClass {
    fun fun1() {
        println("fun1 호출")
    }

    fun fun2(param: String) {
        println("fun2 호출")
    }

    fun fun3(param: Any?) {
        println("fun3 호출")
    }

    fun fun4() {
        println("fun4 호출")
    }
}

fun executeAll(obj: Any) {
    val kClass = obj::class
    // 1. Executable 어노테이션이 붙어있는 지 검사
    if(!kClass.hasAnnotation<Executable>()) return
    val callable = kClass.memberFunctions
        .filter { it.returnType == Unit::class.createType() } // 2. 리턴 타입이 Unit인지 검사
        .filter { it.parameters.size == 1 && it.parameters[0].type == kClass.createType() } // 3. 파라미터가 없는지 검사

    // 4. 자기 자신 호출
    callable.forEach { it.call(obj) }
}