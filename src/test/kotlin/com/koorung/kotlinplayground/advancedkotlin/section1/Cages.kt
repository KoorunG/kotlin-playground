package com.koorung.kotlinplayground.advancedkotlin.section1

// 1. 제네릭을 사용하지 않는 Cage
class Cage(
    // 무공변인 MutableList로 animals 프로퍼티 선언
    private val animals: MutableList<Animal> = mutableListOf(),
) {
    fun getFirst(): Animal = animals.first()
    fun clear() {
        animals.clear()
    }

    fun put(aniaml: Animal) {
        animals.add(aniaml)
    }

    fun moveFrom(otherCage: Cage) {
        this.animals.addAll(otherCage.animals)
    }
}

// 2. 제네릭을 사용한 GenericCage<T>
class GenericCage<T>(
    private val animals: MutableList<T> = mutableListOf(),
) {
    fun getFirst(): T = animals.first()
    fun clear() {
        animals.clear()
    }

    fun put(aniaml: T) {
        animals.add(aniaml)
    }

    /* 변성을 주지 않고 만든 메소드 */
    fun moveFrom(otherCage: GenericCage<T>) {
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: GenericCage<T>) {
        otherCage.animals.addAll(this.animals)
    }
    /* ++++++++++++++++++++ */


    // out 키워드로 공변(covarient) 관계를 만들어준다..!
    // 변성을 준 otherCage는 데이터를 꺼날수만 있고, 넣을 수 없다..!
    fun covarientMoveFrom(otherCage: GenericCage<out T>) {
        this.animals.addAll(otherCage.animals)
    }

    // in 키워드로 반공변(contravarient) 관계를 만들어준다.
    fun contravarientMoveTo(otherCage: GenericCage<in T>) {
        otherCage.animals.addAll(this.animals)
    }
}

// 클래스 자체를 공변하게 만드는 경우
class OutCage<out T>(
    private val animals: MutableList<T> = mutableListOf(),
) {
    fun getFirst(): T = animals.first()
    fun getAll(): List<T> = this.animals

//    // 생산만 할 수 있기 때문에 컴파일에러가 발생한다
//    fun put(aniaml: T) {
//        animals.add(aniaml)
//    }
}

// 클래스 자체를 반공변하게 만드는 경우
class InCage<in T>(
    private val animals: MutableList<T> = mutableListOf(),
) {
    // 컴파일에러가 발생한다.
//    fun getFirst(): T = animals.first()
//    fun getAll(): List<T> = this.animals
    fun put(animal: T) {
        animals.add(animal)
    }

    fun putAll(animals: List<T>) {
        this.animals.addAll(animals)
    }

//    // 소비만 할 수 있기 때문에 컴파일에러가 발생한다.
//    fun getFirst(): T = animals.first()
}


// 타입파라미터에 제네릭 제약을 주는 경우
class ConstraintsCage<T : Animal>() {}

// 제네릭제약이 여러개일 경우 'where' 키워드를 사용한다.
class MultiConstraintsCage<T>(
    private val animals: MutableList<T> = mutableListOf(),
) where T : Animal, T : Comparable<T> {

    fun getFirst(): T = animals.first()
    fun getAll(): List<T> = this.animals

    fun put(animal: T) {
        animals.add(animal)
    }

    fun putAll(animals: List<T>) {
        this.animals.addAll(animals)
    }

    fun printAfterSorting() {
        // Comparable<T>가 구현됐기 때문에 가능
        this.animals.sorted()
            .map { it.name }
            .let(::println)
    }
}