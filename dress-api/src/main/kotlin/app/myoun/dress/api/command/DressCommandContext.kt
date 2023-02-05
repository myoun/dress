package app.myoun.dress.api.command

import kotlin.reflect.KProperty

interface DressCommandContext {
    val input: String

    operator fun <T> get(name: String, clazz: Class<T>): T
}

inline operator fun <reified T> DressCommandContext.get(name: String): T {
    return this[name, T::class.java]
}

inline operator fun <reified T> DressCommandContext.getValue(
    thisRef: Any?,
    property: KProperty<*>
): T {
    return this[property.name, T::class.java]
}