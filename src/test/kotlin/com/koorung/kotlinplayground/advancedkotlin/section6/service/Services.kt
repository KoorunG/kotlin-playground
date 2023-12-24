package com.koorung.kotlinplayground.advancedkotlin.section6.service

annotation class MyComponents

class Services

@MyComponents
class AService {
    fun introduce() {
        println("AService 입니다.")
    }
}

@MyComponents
class BService(
    private val aService: AService
) {
    fun introduce() {
        aService.introduce()
    }
}