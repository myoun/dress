package app.myoun.dress.api.command.internal

import app.myoun.dress.api.command.DressCommand
import app.myoun.dress.api.command.node.AbstractCommandNode
import app.myoun.dress.api.command.node.ArgumentNode
import app.myoun.dress.api.command.node.RootNode
import app.myoun.dress.api.command.node.internal.ArgumentNodeImpl
import app.myoun.dress.api.command.node.internal.LiteralNodeImpl
import app.myoun.dress.api.command.node.internal.RootNodeImpl
import app.myoun.dress.api.scheduler.minecraft
import app.myoun.dress.api.scope
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.minecraft.server.command.ServerCommandSource

class DressCommandImpl : DressCommand {

    private val _commands: ArrayList<(CommandDispatcher<ServerCommandSource>) -> Unit> = arrayListOf()
    override val commands
        get() = _commands as List<(CommandDispatcher<*>) -> Unit>

    override fun register(name: String, init: RootNode.() -> Unit) {
        val root = RootNodeImpl().apply { initialize(name) }.apply(init)

        _commands.add { dispatcher ->
            dispatcher.register(
                rootThen(literal(name), root)
            )
        }
    }


    private fun rootThen(builder: LiteralArgumentBuilder<ServerCommandSource>, node: AbstractCommandNode): LiteralArgumentBuilder<ServerCommandSource> {
        var b = builder
        if (node.executes != null) {
            b = b.executes {
                node.executes!!.invoke(it.source.toDressCommandSource(), DressCommandContextImpl(it.input, it))
                1
            }
        }
        if (node.requires != null) {
            b = b.requires {
                node.requires!!.invoke(it.toDressCommandSource())
            }
        }
        if (node.nodes.size > 0) {
            node.nodes.forEach { child ->
                var b1: ArgumentBuilder<ServerCommandSource, *>? = null
                if (child is ArgumentNodeImpl) {
                    b1 = argument(child.name, child.argument)
                } else if (child is LiteralNodeImpl) {
                    b1 = literal(child.name)
                }

                if (b1 != null) {
                    b = b.then(nodeThen(b1, child))
                }
            }
        }

        return b
    }

    private fun nodeThen(builder: ArgumentBuilder<ServerCommandSource, *>, node: AbstractCommandNode): ArgumentBuilder<ServerCommandSource, *> {
        var b: ArgumentBuilder<ServerCommandSource, *> = builder
        if (node.executes != null) {
            b = b.executes {
                scope.launch(Dispatchers.minecraft) {
                    println(node.executes)
                    node.executes!!(
                        (it.source as ServerCommandSource).toDressCommandSource(),
                        DressCommandContextImpl(it.input, it)
                    )
                }
                1
            } as ArgumentBuilder<ServerCommandSource, *>
        }
        if (node.requires != null) {
            b = b.requires {
                node.requires!!.invoke((it as ServerCommandSource).toDressCommandSource())
            } as ArgumentBuilder<ServerCommandSource, *>
        }


        if (node.nodes.isNotEmpty()) {
            node.nodes.forEach { child ->
                var b1: ArgumentBuilder<ServerCommandSource, *>? = null
                if (child is ArgumentNodeImpl) {
                    b1 = argument(child.name, child.argument)
                } else if (child is LiteralNodeImpl) {
                    b1 = literal(child.name)
                }
                if (b1 != null) {
                    b = b.then(nodeThen(b1, child)) as ArgumentBuilder<ServerCommandSource, *>
                }
            }
        }

        return b
    }
}

sealed class Or<L, R> {

    data class Left<L, R>(val left: L): Or<L, R>()
    data class Right<L, R>(val right: R): Or<L, R>()

}