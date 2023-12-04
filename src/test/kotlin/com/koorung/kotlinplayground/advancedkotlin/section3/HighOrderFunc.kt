package com.koorung.kotlinplayground.advancedkotlin.section3

// 코틀린에서는 파라미터와 리턴타입에 함수타입이 올 수 있다!
fun compute(num1: Int, num2: Int, op: (Int, Int) -> Int): Int = op(num1, num2)

fun iterate(numbers: List<Int>, exec: (Int) -> Unit) {
    for (n in numbers) {
        exec(n)
    }
}

fun calculate(num1: Int, num2: Int, oper: Char): Int = when(oper) {
    '+' -> num1 + num2
    '-' -> num1 - num2
    '*' -> num1 * num2
    '/' -> {
        if(num2 == 0) throw IllegalArgumentException("0으로 나눌 수 없습니다")
        else num1 / num2
    }
    else -> throw IllegalArgumentException("들어올 수 없는 연산자입니다 ::: [$oper]")
}

enum class Operator(
    private val oper: Char,
    val calFunc: (Int, Int) -> Int
) {
    PLUS('+', {a, b -> a + b}),
    MINUS('-', {a, b -> a - b}),
    MULTIPLY('*', {a, b -> a * b}),
    DIVIDE('/', {a, b ->
        if(b == 0) throw IllegalArgumentException("0으로 나눌 수 없습니다")
        else a / b
    });
}

fun calculate2(num1: Int, num2: Int, oper: Operator): Int = oper.calFunc(num1, num2)