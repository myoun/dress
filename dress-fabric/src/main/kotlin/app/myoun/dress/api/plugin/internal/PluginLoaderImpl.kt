package app.myoun.dress.api.plugin.internal

import app.myoun.dress.api.gson
import app.myoun.dress.api.plugin.DressPlugin
import app.myoun.dress.api.plugin.Plugin
import app.myoun.dress.api.plugin.PluginInfo
import app.myoun.dress.api.plugin.PluginLoader
import app.myoun.dress.api.scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile
import kotlin.reflect.full.createInstance

class PluginLoaderImpl: PluginLoader {

    private val _plugins = mutableSetOf<DressPlugin>()
    override val plugins: Set<DressPlugin>
        get() = _plugins

    override fun loadPlugin(file: File): DressPlugin {
        val jar = JarFile(file, true)
        val entries = jar.entries()

        val pluginInfo = gson.fromJson(InputStreamReader(jar.getInputStream(jar.getJarEntry("plugin.json"))), PluginInfo::class.java)
        val urls = arrayOf(URL("jar:file:${file.path}!/"))
        val cl = URLClassLoader.newInstance(urls, javaClass.classLoader)
        var plugin: DressPlugin? = null
        while (entries.hasMoreElements()) {
            val je = entries.nextElement() ?: break
            if(je.isDirectory || !je.name.endsWith(".class")){
                continue;
            }
            var className = je.name.substring(0, je.name.length - 6)
            className = className.replace('/', '.')
            val c = cl.loadClass(className)
            if (c.name == pluginInfo.main) {
                plugin = c.kotlin.createInstance() as DressPlugin
            }
        }

        requireNotNull(plugin) { "Cannot find plugin ${pluginInfo.name}" }

        _plugins.add(plugin)
        plugin.onLoad()

        return plugin
    }

    override fun enablePlugin(plugin: Plugin) {
        scope.launch {
            plugin.onEnable()
        }
    }

    override fun disablePlugin(plugin: Plugin) {
        scope.launch {
            plugin.onDisable()
        }
        _plugins.remove(plugin)
    }
}