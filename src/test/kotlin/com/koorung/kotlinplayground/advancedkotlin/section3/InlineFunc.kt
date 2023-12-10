package com.koorung.kotlinplayground.advancedkotlin.section3

// inline 함수: 함수를 호출하는 쪽에 함수본문 코드를 붙여넣는(inline) 기능
// 고차함수나 클로저등의 사용으로 발생하는 오버헤드를 방지하는데 유용하다.


inline fun repeat(times: Int, func: () -> Unit) {
    for (i in 1..times) {
        func()
    }
}

inline fun repeat2(times: Int, noinline func: () -> Unit) {
    for (i in 1..times) {
        func()
    }
}

inline fun repeat3(times: Int, crossinline func: () -> Unit) {
    for (i in 1..times) {
        func()
    }
}

fun main() {

    repeat(2) {
        println("Hello world")
        return  // 그냥 inline 함수의 함수파라미터에는 람다식에서 허용되지 않는 return을 사용할 수 있다.
                // 이는 예상치 못한 결과를 가져오므로 막는것이 좋다.
    }

    repeat2(2) {
        println("Hello world")
//        return
    }

    repeat3(2) {
        println("Hello world")
//        return  // 함수 파라미터에 crossinline 키워드를 붙이면 non-local return을 방지할 수 있다. (noinline도 자동으로 방지되는듯)
    }
}