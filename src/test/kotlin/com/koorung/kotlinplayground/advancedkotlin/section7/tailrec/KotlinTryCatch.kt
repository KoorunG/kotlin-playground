package com.koorung.kotlinplayground.advancedkotlin.section7.tailrec

// Java와는 다르게 Kotlin에서는 여러 예외를 처리하는것이 불가능하다
// when expression을 사용하여 우회하자

class AException: RuntimeException()
class BException: RuntimeException()
class CException: RuntimeException()

fun throwsException(n: Int) {
    when {
        n > 0 -> throw AException()
        n == 0 -> throw BException()
        else -> throw CException()
    }
}