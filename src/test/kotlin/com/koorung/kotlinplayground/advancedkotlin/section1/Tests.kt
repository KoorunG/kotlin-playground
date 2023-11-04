package com.koorung.kotlinplayground.advancedkotlin.section1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Tests {

    @Test
    fun `타입캐스팅으로 잉어 Cage로 캐스팅`() {
        val cage = Cage()
        // 잉어를 넣는다.
        cage.put(Carp("잉어"))
        // 컴파일에러가 발생한다.
//        val carp: Carp = cage.getFirst()

        // 1. as로 타입캐스팅
        val carp = cage.getFirst() as Carp
        println(carp)

        // 런타임에 에러가 발생하므로 위험하다..
        assertThrows<ClassCastException> {
            // 케이지를 비우고 금붕어를 넣는다면?
            cage.clear()
            cage.put(GoldFish("금붕어"))
            // ClassCastException 발생...
            val carp2 = cage.getFirst() as Carp
            println(carp2)
        }

        // 2. as?로 타입캐스팅
        assertThrows<IllegalArgumentException> {
            // 케이지를 비우고 금붕어를 넣는다면?
            cage.clear()
            cage.put(GoldFish("금붕어"))
            // ClassCastException 발생...
            val carp3 = cage.getFirst() as? Carp ?: throw IllegalArgumentException("잉어만 넣을 수 있습니다!")
            println(carp3)
        }
    }

    @Test
    fun `제네릭을 사용하여 잉어 Cage를 명시적으로 생성`() {
        val carpGenericCage = GenericCage<Carp>()
//        carpGenericCage.put(GoldFish("금붕어"))    // 컴파일에러
        carpGenericCage.put(Carp("잉어"))
        val carp = carpGenericCage.getFirst()
        println(carp)
    }

    @Test
    fun `무공변한 관계에서는 금붕어 Cage를 물고기 Cage에 옮겨담을 수 없다`() {
        val fishCage = GenericCage<Fish>()
        val goldFishCage = GenericCage<GoldFish>()
        goldFishCage.put(GoldFish("금붕어"))

        // Cage<Fish> 와 Cage<GoldFish> 간의 연관성이 없으므로 컴파일에러가 발생한다..!
        // 이를 무공변(invarient)하다 라고 한다.
//        fishCage.moveFrom(goldFishCage)

        // Cage<Fish>에 GoldFish를 넣는 것은 정상동작한다.
        fishCage.put(GoldFish("금붕어"))

    }

    @Test
    fun `공변관계로 만들어주면 금붕어 Cage를 물고기 Cage에 옮겨담을 수 있다`() {
        val fishCage = GenericCage<Fish>()
        val goldFishCage = GenericCage<GoldFish>()
        goldFishCage.put(GoldFish("금붕어"))

        // 타입파라미터에 'out' 키워드를 사용하여 공변으로 변성을 주었음
        fishCage.covarientMoveFrom(goldFishCage)
    }

    @Test
    fun `무공변한 관계에서는 상대 Cage에 내 Cage의 물고기를 옮겨담을 수 없다`() {
        val fishCage = GenericCage<Fish>()
        val goldFishCage = GenericCage<GoldFish>()
        goldFishCage.put(GoldFish("금붕어"))

        // Cage<Fish> 와 Cage<GoldFish> 간의 연관성이 없으므로 컴파일에러가 발생한다..!
        // 이를 무공변(invarient)하다 라고 한다.
//        goldFishCage.moveTo(fishCage)
    }

    @Test
    fun `반공변한 관계에서 상대 Cage에 내 Cage의 물고기 옮겨담기`() {
        val fishCage = GenericCage<Fish>()
        val goldFishCage = GenericCage<GoldFish>()
        goldFishCage.put(GoldFish("금붕어"))

        // Cage<Fish> 와 Cage<GoldFish> 간의 연관성이 없으므로 컴파일에러가 발생한다..!
        // 이를 반공변(contravarient)하다 라고 한다.
        goldFishCage.contravarientMoveTo(fishCage)
    }

    @Test
    fun `클래스 자체를 변성하는 경우`() {
        val fishCage = OutCage<Fish>()
        val goldFishCage = OutCage<GoldFish>()

        // OutCage<out T> 자체가 공변한 클래스이기 때문에 가능!
        var animalCage: OutCage<Animal> = fishCage
        animalCage = goldFishCage
    }

    @Test
    fun `제네릭 제약을 준 클래스 사용`() {
        val birdsCage = MultiConstraintsCage(animals = mutableListOf(Eagle(), Sparrow()))
        birdsCage.printAfterSorting()
    }

    @Test
    fun `타입소거 및 Star Projection`(){
        fun checkMutableList(data: Any) {
            if (data is MutableList<*>) {
//        data.add(3) // MutableList 타입이므로 .add()는 사용할 수 있으나, 정확한 타입을 알 수 없으므로 실제로 동작하지 않는다 (컴파일에러)
            }
        }
        fun checkList(data: Any) {
//    if(data is List<String>) {                             // 컴파일에러 (erased type 검사 불가능)
//        println("List<String> 타입임")
//    }

            if (data is List<*>) {
                println("List 타입임 :::: ${data::class.simpleName}") // ArrayList

                val d = data[0] // List의 기능은 사용할 수 있으나 제네릭 정보를 알 수 없으므로 Any>
            }
        }

        checkList(listOf(1, 2, 3))
        checkMutableList(listOf(1, 2, 3))
    }

    @Test
    fun `제네릭함수에서도 타입소거가 발생하며 타입정보를 유지하려면 reifeid 키워드를 사용해아한다`() {
        val num = 3
        val str = "test"

        assertThat(num.toSuperString()).isEqualTo("Int: 3")
        assertThat(str.toSuperString()).isEqualTo("String: test")
    }

    @Test
    fun `Star projection과 reified 키워드 동시 활용 예제`() {

        // 주어진 리스트에 T 타입을 가진 원소가 하나라도 있으면 true, 아니면 false를 리턴하는 함수
        val testList = listOf(1, 2, 3, true, 4)
        val testList2 = listOf("A", "B", "C", "D", 5)

        assertThat(testList.hasAnyInstanceOf<String>()).isFalse()
        assertThat(testList2.hasAnyInstanceOf<Int>()).isTrue()
    }

}

