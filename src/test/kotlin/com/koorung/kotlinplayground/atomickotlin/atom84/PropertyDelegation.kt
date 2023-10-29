package com.koorung.kotlinplayground.atomickotlin.atom84

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty

/**
 * 코틀린의 프로퍼티 위임
 *
 * 코틀린에서는 by 키워드를 사용하여 프로퍼티를 위임과 연결할 수 있다!
 * => var(val) 프로퍼티이름 by 위임객체이름
 *
 * 프로퍼티가 val(읽기전용) 일 경우 위임객체의 클래스에 getValue()가 정의되어있어야 하며
 * 프로퍼티가 var(수정가능) 일 경우 getValue(), setValue()가 정의되어있어야 한다.
 */
class PropertyDelegation {

    @Test
    fun `val 프로퍼티 위임`() {
        val readable = Readable(11)
        assertThat(readable.i).isEqualTo(11)    // 직접 프로퍼티에 접근한 값
        assertThat(readable.value).isEqualTo("getValue: 11")    // 'String by BasicReadable()' 으로 위임받은 값
    }

    @Test
    fun `var 프로퍼티 위임`() {
        val readAndWritable = ReadAndWritable(11)

        assertThat(readAndWritable.i).isEqualTo(11)
        assertThat(readAndWritable.value).isEqualTo("getValue: 11")
        assertThat(readAndWritable.msg).isEmpty()

        // value를 세팅
        readAndWritable.value = "30"

        assertThat(readAndWritable.i).isEqualTo(30)
        assertThat(readAndWritable.value).isEqualTo("getValue: 30")
        assertThat(readAndWritable.msg).isNotEmpty()
        assertThat(readAndWritable.msg).isEqualTo("setValue to 30")

    }
}

class Readable(
    val i: Int
) {
    val value: String by BasicReadable()
}

class BasicReadable {
    operator fun getValue(
        readable: Readable,
        property: KProperty<*>
    ): String = "getValue: ${readable.i}"
}

class ReadAndWritable(
    var i: Int
) {
    var msg: String = ""
    var value: String by BasicReadAndWritable()
}

class BasicReadAndWritable {
    operator fun getValue(
        readAndWritable: ReadAndWritable,
        property: KProperty<*>
    ): String = "getValue: ${readAndWritable.i}"

    operator fun setValue(
        readAndWritable: ReadAndWritable,
        property: KProperty<*>,
        s: String) {
        readAndWritable.i = s.toIntOrNull() ?: 0
        readAndWritable.msg = "setValue to ${readAndWritable.i}"
    }
}