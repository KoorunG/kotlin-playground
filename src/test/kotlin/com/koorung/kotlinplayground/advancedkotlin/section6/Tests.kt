package com.koorung.kotlinplayground.advancedkotlin.section6

import com.koorung.kotlinplayground.advancedkotlin.section6.container.ContainerV1
import com.koorung.kotlinplayground.advancedkotlin.section6.container.ContainerV2
import com.koorung.kotlinplayground.advancedkotlin.section6.container.ContainerV3
import com.koorung.kotlinplayground.advancedkotlin.section6.container.start
import com.koorung.kotlinplayground.advancedkotlin.section6.service.AService
import com.koorung.kotlinplayground.advancedkotlin.section6.service.BService
import com.koorung.kotlinplayground.advancedkotlin.section6.service.Services
import com.koorung.kotlinplayground.advancedkotlin.section6.typesafe.Carp
import com.koorung.kotlinplayground.advancedkotlin.section6.typesafe.GoldFish
import com.koorung.kotlinplayground.advancedkotlin.section6.typesafe.TypeSafeCage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Tests {

    @Test
    fun `ContainerV1에 인스턴스 등록하고 꺼내오기 - 생성자에 파라미터가 없는 경우만 처리 가능`() {
        // 등록
        ContainerV1.register(AService::class)
        // getInstance()
        val instance = ContainerV1.getInstance(AService::class)
        instance.introduce()
    }

    @Test
    fun `ContainerV2에 인스턴스 등록하고 꺼내오기 - 생성자에 파라미터가 존재하고 그 파라미터가 DI에 존재할 때 주입`() {
        // 등록
        ContainerV2.register(AService::class)
        ContainerV2.register(BService::class)

        val instance = ContainerV2.getInstance(BService::class)
        instance.introduce()
    }

    @Test
    fun `java의 Reflections 패키지 이용하기`() {
        start(Services::class)
        val instance = ContainerV3.getInstance(BService::class)
        instance.introduce()
    }

    @Test
    fun `리플렉션을 이용하여 type-safe한 collection 만들기`(){
        val cage = TypeSafeCage()
        cage.putOne(Carp::class, Carp("잉어"))
        cage.putOne(GoldFish::class, GoldFish("금붕어"))

        val goldFish = cage.getOne(GoldFish::class)

        assertThat(goldFish).isExactlyInstanceOf(GoldFish::class.java)
    }

    @Test
    fun `inline reified 키워드를 사용하여 제네릭도 지원하는 함수 만들어보기`(){
        val cage = TypeSafeCage()
        cage.putOne(Carp::class, Carp("잉어"))
        cage.putOne(GoldFish::class, GoldFish("금붕어"))

        val carp = cage.getOne<Carp>()

        assertThat(carp).isExactlyInstanceOf(Carp::class.java)
    }
}