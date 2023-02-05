package app.myoun.dress.api.plugin

import java.io.File
import java.io.IOException
import kotlin.jvm.Throws

interface PluginLoader {

    val plugins: Set<DressPlugin>

    fun loadPlugin(file: File): DressPlugin

    fun enablePlugin(plugin: Plugin)

    fun disablePlugin(plugin: Plugin)
}