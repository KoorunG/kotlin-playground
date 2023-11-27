# 코틀린 고급편 강의내용 정리

[인프런 강의 - 코틀린 고급편 (김대현)](https://www.inflearn.com/course/%EC%BD%94%ED%8B%80%EB%A6%B0-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard)

### Section1 : 제네릭클래스, 함수 활용법
 
#### 1. 제네릭 클래스, 인터페이스, 함수
* 코틀린에서는 클래스, 인터페이스, 함수 등을 제네릭 버전으로 만들 수 있다.

```kotlin
// 제네릭 클래스
class CGeneric<T>

// 제네릭 인터페이스 
interface IGeneric<T>

// 제네릭 함수
fun <T> fGeneric(t: T) {}
```


#### 2. 타입변성
* 타입파라미터간의 상하관계가 존재할 때 제네릭<타입파라미터> 간에도 상하관계를 만드는 기법
* 기본적으로 무공변(invarient) 하며 `out` 키워드로 공변(covarient), `in` 키워드로 반공변(contravarient) 상태로 만들 수 있다..!
* 특정함수나 특정변수만 변성할 수 있으며, 클래스 자체를 변성하는 것도 가능하다!
<br/>

* [Cages.kt](section1/Cages.kt) 및 [Tests.kt](section1/Tests.kt) 참조...


#### 3. 제네릭 제약
* 제네릭 클래스나 인터페이스에서 타입파라미터에 제약을 걸 수 있다.

```kotlin
class CGeneric<T: Any> // Nullable이 오지 못하도록 타입제한
```


#### 4. 타입소거, Star projection
* 기본적으로 JVM기반 언어에서는 **런타임에 타입정보가 소거된다..!**
* 코틀린에서도 자바와의 하위호환성을 위해 타입소거가 발생하지만, `inline` + `reified` 키워드로 타입소거를 막는 것도 가능하다.
* 코틀린은 제네릭 사용 시 타입파라미터가 디폴트 설정이므로 타입을 `<*>` 로 선언하여 모든타입을 가져올 수 있다. 
```kotlin
// List<*> 타입의 수신객체가 <T> 타입을 하나라도 가지고 있는지를 리턴하는 확장함수
inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean = this.any { it is T }
```

#### 5. 제네릭 클래스 상속
* 제네릭 클래스를 상속하는 것이 가능하다. 단 부모 클래스에 타입파라미터를 제대로 전달해야 한다.
```kotlin
open class Parents<T: Any>

// 1. 부모의 제약조건을 그대로 설정한 타입파라미터를 넘기는 방법
class Children1<T: Any>: Parents<T>()
// 2. 구체적인 타입을 넘기는 방법
class Children2: Parents<Any>() 
```

#### 6. typeAlias
* 코틀린에서는 `typealias` 키워드를 사용하여 타입에 별칭을 지정하여 사용할 수 있다.

```kotlin

class PersonDtoKey
class PersonDto

// 타입별칭을 지정
typealias PersonDtoStore = Map<PersonDtoKey, MutableList<PersonDto>>

// 1. 파라미터가 너무 길다.
fun handleCacheStore(store: Map<PersonDtoKey, MutableList<PersonDto>>) {}

// 2. 파라미터를 깔끔하게 정리하는 것이 가능하다!
fun handleCacheStore(store: PersonDtoStore) {}
```

---

### Section2 : 코틀린의 지연과 위임 처리


#### 1. lateinit 키워드
* 코틀린에서는 `lateinit` 키워드를 이용하여 인스턴스화 시점과 변수 초기화 시점을 분리할 수 있다.
* 즉, 일단 인스턴스를 생성하는것은 가능하지만 초기화하지 않으면 예외가 발생하는 객체를 만들 수 있다..!
```kotlin
class Person {
    lateinit var name: String
}

val person = Person()

println(person.name) // name 프로퍼티가 초기화되지 않았으므로 UninitializedPropertyAccessException 발생..!

val initPerson = person.apply { this.name = "이재학" }
println(initPerson)  // "이재학"
```

#### 2. by lazy 키워드
* val 프로퍼티에 `by lazy` + `람다` 를 이용하여 <br/>
**해당 프로퍼티의 getter가 최초 호출될 때만** 로직이 실행되도록 할 수 있다.
```kotlin
class Person {
    val name: String by lazy {
        Thread.sleep(2_000)
        "디폴트네임"
    }
    val age: Int = 0
}

val person = Person()
println(person.name)  // 2초후 "디폴트네임"
println(person.age)   // 즉시 "0"
```