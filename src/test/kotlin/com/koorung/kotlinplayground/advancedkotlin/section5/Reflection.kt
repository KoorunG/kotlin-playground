package com.koorung.kotlinplayground.advancedkotlin.section5

import kotlin.reflect.cast
import kotlin.reflect.full.createType

class Reflection

// KClass<T>.cast(...)
// 인자를 T 타입으로 캐스팅한다.
inline fun <reified T: Any> castToType(obj: Any): T = T::class.cast(obj)

// 인자의 KType 리플랙션을 얻는다.
fun typeOf(obj: Any) = obj::class.createType()