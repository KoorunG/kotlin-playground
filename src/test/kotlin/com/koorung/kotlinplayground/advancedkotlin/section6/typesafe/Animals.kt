package com.koorung.kotlinplayground.advancedkotlin.section6.typesafe

abstract class Animal (
    open val name: String,
)

// 1. 생선
abstract class Fish(
    name: String
): Animal(name)

// 금붕어
data class GoldFish(
    override val name: String
): Fish(name)

// 잉어
data class Carp(
    override val name: String
): Fish(name)

// 2. 새
abstract class Bird(
    name: String,
    private val size: Int
): Animal(name), Comparable<Bird> {
    override fun compareTo(other: Bird): Int = this.size.compareTo(other.size)
}

// 참새
class Sparrow: Bird(name = "참새", size = 100)
// 독수리
class Eagle: Bird(name = "독수리", size = 300)