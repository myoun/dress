package app.myoun.dress.api.plugin

import app.myoun.dress.api.gson
import org.slf4j.LoggerFactory
import java.io.InputStreamReader

abstract class DressPlugin : Plugin {

    override val info: PluginInfo = gson.fromJson(InputStreamReader(javaClass.classLoader.getResourceAsStream("plugin.json")), PluginInfo::class.java)
    val logger = LoggerFactory.getLogger(info.name)
}