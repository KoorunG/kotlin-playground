package com.koorung.kotlinplayground.advancedkotlin.section5

class GoldFish(
    val name: String,
) {
    fun print() {
        println("금붕어의 이름은 $name 입니다.")
    }

    fun name() = this.name
}