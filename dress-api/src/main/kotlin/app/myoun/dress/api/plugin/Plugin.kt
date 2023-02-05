package app.myoun.dress.api.plugin

interface Plugin {
    val info: PluginInfo

    suspend fun onEnable()
    suspend fun onDisable()
    fun onLoad()
}