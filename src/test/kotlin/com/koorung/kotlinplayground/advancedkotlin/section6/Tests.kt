package com.koorung.kotlinplayground.advancedkotlin.section6

import com.koorung.kotlinplayground.advancedkotlin.section6.container.ContainerV1
import com.koorung.kotlinplayground.advancedkotlin.section6.container.ContainerV2
import com.koorung.kotlinplayground.advancedkotlin.section6.container.ContainerV3
import com.koorung.kotlinplayground.advancedkotlin.section6.container.start
import com.koorung.kotlinplayground.advancedkotlin.section6.service.AService
import com.koorung.kotlinplayground.advancedkotlin.section6.service.BService
import com.koorung.kotlinplayground.advancedkotlin.section6.service.Services
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
}