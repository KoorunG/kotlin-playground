package com.koorung.kotlinplayground.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.math.abs

class OkkyTest {

    @Test
    fun test1() {
        val result = mutableListOf<Int>() // 결과
        val added = mutableListOf<Int>()  // 추가된 숫자리스트
        val remain = mutableListOf<Int>() // 남아있는 숫자리스트
        val list = mutableListOf(3, 7, 4, 1, 2, 1, 2, 2, 2, 3, 3, 4, 5, 2, 10, 1, 1)

        // 정렬
        list.sort()

        // list가 빌 때 까지 반복
        while(list.isNotEmpty()){
            // added에 list.first()가 존재한다면
            if(added.contains(list.first())) {
                // remain에 list.first()를 추가 && list에서 제거
                remain.add(list.removeFirst())
            } else {
                // 아닐경우 added에 list.first()를 추가 && list에서 제거
                added.add(list.removeFirst())
            }

            // list가 비어있을 경우
            // list에 remain의 요소 모두 추가
            // result에 added의 요소 모두 추가
            if(list.isEmpty()) {
                list.addAll(remain)
                result.addAll(added)
                // remain과 added 초기화
                remain.clear()
                added.clear()
            }
        }

        print(result) // [1, 2, 3, 4, 5, 7, 10, 1, 2, 3, 4, 1, 2, 3, 1, 2, 2]
    }

    @Test
    fun test2(){
        val list = mutableListOf(3, 7, 4, 1, 2, 1, 2, 2, 2, 3, 3, 4, 5, 2, 10, 1, 1)
        val result = mutableListOf<Int>()
        while(list.isNotEmpty()) {
            list.distinct().sorted().forEach {
                result.add(list.removeAt(list.indexOf(it)))
            }
        }
        print(result)
    }

    /**
     * 정렬된 정수형 배열 arr과 정수 k, x가 주어진다.
     * k는 개수, x는 기준값으로 x와의 절대값 차이 기준으로 가까운 수를 k 개수만큼 정렬된 순서로 출력해야 한다.
     * 절대값 차이가 같을 경우 숫자가 작은 것이 먼저 출력되도록 한다.
     */
    @Test
    fun test3() {

        val arr = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) // 1..10 배열
        val x = 5   // 기준값
        val k = 5   // 개수

        val result = arr
            .map { abs(it - x ) to it }              // 1. Pair<abs(diff), value> 생성
            .sortedWith(
                compareBy( { it.first }, { it.second }) // 2. first로 우선 정렬하고 동일하다면 second로 정렬
            )
            .slice(0 until k)             // 3. k 개수만큼 슬라이스
            .map { it.second }                          // 4. List<Pair<Int, Int>> -> List<Int>
            .sorted()                                   // 5. 재정렬
            .toIntArray()

        println(arr.toList())
        println(result.toList())
        assertThat(result).isEqualTo(intArrayOf(3, 4, 5, 6, 7))
    }
}