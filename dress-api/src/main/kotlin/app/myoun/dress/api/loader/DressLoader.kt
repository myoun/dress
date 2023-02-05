package app.myoun.dress.api.loader

import java.lang.reflect.InvocationTargetException

/**
 * Dress Implementation Loader
 *
 * See also: [Kommand](https://github.com/monun/kommand/blob/master/kommand-api/src/main/kotlin/io/github/monun/kommand/loader/LibraryLoader.kt)
 */
object DressLoader {



    fun <T> loadImplement(type: Class<T>, vararg constructorArgs: Any? = emptyArray()): T {
        val implPackageName = "${type.`package`.name}.internal"
        val className = "${type.simpleName}Impl"
        val parameterTypes = constructorArgs.map { it?.javaClass }.toTypedArray()

        return try {
            val implClass = Class.forName("${implPackageName}.${className}", true, type.classLoader).asSubclass(type).asSubclass(type)
            val constructor = kotlin.runCatching {
                implClass.getConstructor(*parameterTypes)
            }.getOrNull() ?: throw UnsupportedOperationException("${type.name} does not have Constructor for [${parameterTypes.joinToString()}]")
            constructor.newInstance(*constructorArgs) as T
        } catch (exception: ClassNotFoundException) {
            throw UnsupportedOperationException("${type.name} a does not have implement", exception)
        } catch (exception: IllegalAccessException) {
            throw UnsupportedOperationException("${type.name} constructor is not visible")
        } catch (exception: InstantiationException) {
            throw UnsupportedOperationException("${type.name} is abstract class")
        } catch (exception: InvocationTargetException) {
            throw UnsupportedOperationException(
                "${type.name} has an error occurred while creating the instance",
                exception
            )
        }
    }
}