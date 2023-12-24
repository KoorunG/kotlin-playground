package com.koorung.kotlinplayground.advancedkotlin.section6.container

import com.koorung.kotlinplayground.advancedkotlin.section6.service.MyComponents
import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.cast

// DI Container (싱글톤으로 만들기 위해 object)
object ContainerV1 {

    // 등록한 클래스를 보관하는 mutableSet<KClass<*>> 선언
    private val registeredClasses = mutableSetOf<KClass<*>>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    // T: Any로 선언하여 null값이 오는 것을 막아준다.
    fun <T : Any> getInstance(type: KClass<T>): T =
        registeredClasses.firstOrNull { it == type } // 1. 컨테이너에서 타입이 일치하는 경우만 꺼내온다.
            ?.let {
                it.constructors.first { cons -> cons.parameters.isEmpty() }.call() as T
            } // 2. 파라미터가 없는 생성자만 call하고 T로 캐스팅한다.
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다.")
}

object ContainerV2 {

    // 등록한 클래스를 보관하는 mutableSet<KClass<*>> 선언
    private val registeredClasses = mutableSetOf<KClass<*>>()
    private val cachedInstance = mutableMapOf<KClass<*>, Any>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    // T: Any로 선언하여 null값이 오는 것을 막아준다.
    fun <T : Any> getInstance(type: KClass<T>): T =
        if (type in cachedInstance) type.cast(cachedInstance[type])
        else registeredClasses.firstOrNull { it == type }
            ?.let { instantiate(it) as T }
            ?: throw IllegalArgumentException("해당 인스턴스를 찾을 수 없습니다.")
                .apply { cachedInstance[type] = this }

    private fun <T : Any> findUsableConstructor(clazz: KClass<T>): KFunction<T> =
        clazz.constructors.firstOrNull { it.parameters.isAllRegistetred }
            ?: throw IllegalArgumentException("사용 가능한 생성자가 없습니다.")

    private val List<KParameter>.isAllRegistetred: Boolean
        get() = this.all { it.type.classifier in registeredClasses }

    private fun <T : Any> instantiate(clazz: KClass<T>): T {
        val constructor = findUsableConstructor(clazz)
        val params = constructor.parameters.map { getInstance(it.type.classifier as KClass<*>) }.toTypedArray()
        return constructor.call(*params)
    }
}

object ContainerV3 {

    // 등록한 클래스를 보관하는 mutableSet<KClass<*>> 선언
    private val registeredClasses = mutableSetOf<KClass<*>>()
    private val cachedInstance = mutableMapOf<KClass<*>, Any>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    // T: Any로 선언하여 null값이 오는 것을 막아준다.
    fun <T : Any> getInstance(type: KClass<T>): T =
        if (type in cachedInstance) type.cast(cachedInstance[type])
        else registeredClasses.firstOrNull { it == type }
            ?.let { instantiate(it) as T }
            ?: throw IllegalArgumentException("해당 인스턴스를 찾을 수 없습니다.")
                .apply { cachedInstance[type] = this }

    private fun <T : Any> findUsableConstructor(clazz: KClass<T>): KFunction<T> =
        clazz.constructors.firstOrNull { it.parameters.isAllRegistetred }
            ?: throw IllegalArgumentException("사용 가능한 생성자가 없습니다.")

    private val List<KParameter>.isAllRegistetred: Boolean
        get() = this.all { it.type.classifier in registeredClasses }

    private fun <T : Any> instantiate(clazz: KClass<T>): T {
        val constructor = findUsableConstructor(clazz)
        val params = constructor.parameters.map { getInstance(it.type.classifier as KClass<*>) }.toTypedArray()
        return constructor.call(*params)
    }

}

fun start(clazz: KClass<*>) {
    val reflections = Reflections(clazz.packageName)
    val jClasses = reflections.getTypesAnnotatedWith(MyComponents::class.java)
    jClasses.forEach {
        println("${it.kotlin}을 등록합니다.")
        ContainerV3.register(it.kotlin)
    }
}

private val KClass<*>.packageName: String
    get() {
        val qualifiedName = this.qualifiedName ?: throw IllegalArgumentException("익명 객체입니다..!")
        val hierarchy = qualifiedName.split(".")
        return hierarchy.subList(0, hierarchy.lastIndex).joinToString(".")
    }