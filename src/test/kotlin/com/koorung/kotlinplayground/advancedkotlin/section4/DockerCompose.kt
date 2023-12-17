package com.koorung.kotlinplayground.advancedkotlin.section4

/**
 * - Docker Compose YAML을 지원하는 Kotlin DSL 만들어보기 실습!
 */
fun main(){
    val yaml = dockerCompose {
        version { 3 }
        service(name = "db") {
            image { "mysql" }
            env("USER" - "myuser")
            env("PASSWORD" - "mypassword")
            port(host = 9999, container = 3306)
        }
    }

    println(yaml.render())
}

fun dockerCompose(init: DockerCompose.() -> Unit): DockerCompose {
    val dockerCompose = DockerCompose()
    dockerCompose.init()
    return dockerCompose
}

@YamlDsl
class DockerCompose {
    private var _version: Int by onceNotNull()
    private val _services = mutableListOf<Service>()

    // init()의 실행결과를 _version 으로 지정
    fun version(init: () -> Int) {
        _version = init()
    }

    fun service(name: String, init: Service.() -> Unit) {
        val service = Service(name)
        service.init()
        _services.add(service)
    }

    // DockerCompose YAML을 랜더링하는 함수
    // indent를 설정할 수 있으며 기본값은 YAML의 디폴트 값인 "  " 로 세팅
    fun render(indent: String = "  "): String {
        val builder = StringBuilder()
        builder.appendNew("version: '$_version'")
        if(_services.size != 0) {
            builder.appendNew("services:")
            _services.joinToString("\n") { it.render(indent) }
                .addIndent(indent, 1)
                .apply { builder.appendNew(this) }
        }
        return builder.toString()
    }
}