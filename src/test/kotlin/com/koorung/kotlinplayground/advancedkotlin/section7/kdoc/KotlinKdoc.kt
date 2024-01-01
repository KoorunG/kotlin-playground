package com.koorung.kotlinplayground.advancedkotlin.section7.kdoc

// kDoc은 코틀린의 문서화 도구임
// jsDoc과 비슷... 하지만 markdown을 지원하고 기타 부가기능이 존재한다.
// @sample: 해당 경로의 내용을 임베드 해서 보여줌
// @see: 해당 경로로 링크를 걸어줌

/**
 * 박스를 나타내는 클래스
 * @param T 박스의 아이템 타입
 * @property name 박스 이름
 * @sample com.koorung.kotlinplayground.advancedkotlin.section7.kdoc.isEmbedded
 * @see com.koorung.kotlinplayground.advancedkotlin.section7.kdoc.isSeen
 */
class Box<T>(
    val name: String
) {
    fun add(item: T): Boolean {
        TODO()
    }
}