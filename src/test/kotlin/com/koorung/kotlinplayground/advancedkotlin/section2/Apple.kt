package com.koorung.kotlinplayground.advancedkotlin.section2

interface Fruit {
    val name: String
    val color: String
    fun bite() {
        println("$color $name 아삭")
    }
}


class Apple: Fruit {
    override val name: String = "사과"
    override val color: String = "빨간색"
}

// 코틀린의 클래스위임 방법
// 프로퍼티로 위임객체를 전달받고 다른 부분만 override로 직접 구현한다.
class GreenApple(
    private val apple: Apple
): Fruit by apple {
    override val color: String = "초록색"
    override fun bite() = super.bite()
}

fun main() {
    val apple = Apple()
    val greenApple = GreenApple(apple)

    apple.bite()
    greenApple.bite()
}