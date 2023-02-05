package app.myoun.dress.api.command

import app.myoun.dress.api.command.node.RootNode
import app.myoun.dress.api.loader.DressLoader
import com.mojang.brigadier.CommandDispatcher

interface DressCommand {
    companion object : DressCommand by DressLoader.loadImplement(DressCommand::class.java)

    val commands: List<(CommandDispatcher<*>) -> Unit>

    fun register(name: String, init: RootNode.() -> Unit)
}