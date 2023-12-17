package com.koorung.kotlinplayground.advancedkotlin.section4

@YamlDsl
class Service(
    val name: String? = null,
) {
    private var _image: String by onceNotNull()
    // YAML에서 여러 값이 올 수 있는 경우 - (대시)로 붙기 때문...
    private val _environments = mutableListOf<Environment>()
    private val _ports = mutableListOf<Port>()
    fun image(init: () -> String) {
        this._image = init()
    }

    fun render(indent: String): String {
        val builder = StringBuilder()
        builder.appendNew("$name:")
        builder.appendNew("image: '$_image'", indent, 1)

        // _environments가 존재할 경우에만 appendNew 호출...
        if(_environments.size != 0) {
            builder.appendNew("environment:", indent, 1)
            _environments.joinToString("\n") { "- ${it.key}: '${it.value}'" }
                .addIndent(indent, 2)
                .apply { builder.appendNew(this) }
        }
        // _ports가 존재할 경우에만 appendNew 호출...
        if(_ports.size != 0) {
            builder.appendNew("port:", indent, 1)
            _ports.joinToString("\n") { "- ${'"'}${it.host}:${it.container}${'"'}" }
                .addIndent(indent, 2)
                .apply { builder.appendNew(this) }
        }
        return builder.toString()
    }

    fun env(environment: Environment) {
        this._environments.add(environment)
    }

    fun port(host: Int, container: Int) {
        this._ports.add(Port(host, container))
    }
}

data class Environment(
    val key: String,
    val value: String,
)

data class Port(
    val host: Int,
    val container: Int,
)