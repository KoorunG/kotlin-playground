package com.koorung.kotlinplayground.advancedkotlin.section7.tailrec

class Key(
    val key: String
)

// @JvmInline 애노테이션을 붙이고 value class로 명시하면
// 해당 클래스는 인라인 클래스가 된다!
// 인라인 클래스는 같은 타입의 프로퍼티를 구분할 때 유용하게 사용될 수 있다..!
@JvmInline
value class InlineKey(
    val key: String
)

// ID의 경우 클래스에 관계없이 흔히 Long 타입으로 매핑될 수 있음
// 이 경우 인라인 클래스로 선언해보자
@JvmInline
value class Id<T>(private val id: Long) {
    override fun toString(): String = id.toString()
}

// id: Id<User>
class User(
    val id: Id<User>,
    val name: String
)

// id: Id<Book>
class Book(
    val id: Id<Book>,
    val name: String
)

fun handle(userId: Id<User>, bookId: Id<Book>) =
     "userId :: $userId, bookId :: $bookId"