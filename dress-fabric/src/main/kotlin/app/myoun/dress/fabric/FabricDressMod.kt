package app.myoun.dress.fabric

import app.myoun.dress.api.command.DressCommand
import app.myoun.dress.api.command.internal.DressCommandImpl
import app.myoun.dress.api.plugin.internal.PluginLoaderImpl
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.kyori.adventure.text.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import java.io.File

object FabricDressMod : DedicatedServerModInitializer {

    val pluginFolder = File("plugins")
    val pluginLoader by lazy { PluginLoaderImpl() }
    lateinit var server: MinecraftServer
        private set
    lateinit var primaryThread: Thread
        private set
    override fun onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTING.register {
            server = it
            primaryThread = it.thread
            // Enabling Plugins
            pluginLoader.plugins.forEach(pluginLoader::enablePlugin)
        }

        ServerLifecycleEvents.SERVER_STOPPING.register {
            pluginLoader.plugins.forEach(pluginLoader::disablePlugin)
        }

        // Plugin Load
        pluginLoad()

    }

    private fun pluginLoad() {
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir()
        } else {
            pluginFolder.listFiles()
                .also {
                    logger.info("Found ${it.size} plugins")
                }?.filter {
                    it.extension == "jar"
                }?.forEach(pluginLoader::loadPlugin)
        }
        // Register Command


        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            DressCommand.commands.forEach {
                it(dispatcher)
            }

            dispatcher.register(LiteralArgumentBuilder.literal<ServerCommandSource?>("hello").then(argument<ServerCommandSource?, String?>("name", StringArgumentType.string()).executes {
                println(it)
                1
            }))
        }
    }
}