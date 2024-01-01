package com.koorung.kotlinplayground.advancedkotlin.section7.tailrec

import kotlin.reflect.KClass

// Java와는 다르게 Kotlin에서는 여러 예외를 처리하는것이 불가능하다
// 1. when expression을 사용하여 우회하거나
// 2. runCatching + 예외처리용 boiler plate을 이용하면 된다. (복잡)

class AException : RuntimeException()
class BException : RuntimeException()
class CException : RuntimeException()

// param에 따라 무조건 예외를 던지는 함수...
fun logic(n: Int) {
    when {
        n > 0 -> throw AException()
        n == 0 -> throw BException()
        else -> throw CException()
    }
}

// --------------- 예외처리를 위한 boiler plate --------------- //

// 상위의 예외가 아래로 내려오지 않게 하기 위한 Wrapper
class ResultWrapper<T>(
    private val result: Result<T>,
    private val knownExceptions: MutableList<KClass<out Throwable>>
) {
    fun toResult(): Result<T> = this.result
    fun onError(vararg exceptions: KClass<out Throwable>,
                action: (Throwable) -> Unit): ResultWrapper<T> {
        this.result.exceptionOrNull()?.let {
            if(it::class in exceptions && it::class !in this.knownExceptions) {
                action(it)
            }
        }
        return this
    }
}

// runCatching의 결과로 리턴되는 Result<T> 타입에 확장함수를 선언함
// 파라미터로 예외 타입토큰을 vararg로 받고
// 람다로 예외를 잡았을 시 실행할 action 을 받는다.
// ResultWrapper<T>이라는 Wrapper으로 감싸서 반환하는데
// 여기에서 잡힌 예외가 하위블록에서 다시 잡히지 않게 하기 위함이다..!
// ResultWrapper<T>의 onError()과 확장함수의 onError() 모두 ResultWrapper<T>를 던지기 때문에
// 메소드 체이닝이 가능하다!
fun <T> Result<T>.onError(vararg exceptions: KClass<out Throwable>,
                          action: (Throwable) -> Unit): ResultWrapper<T> {
    exceptionOrNull()?.let {
        if (it::class in exceptions) {
            action(it)
        }
    }
    return ResultWrapper(this, exceptions.toMutableList())
}