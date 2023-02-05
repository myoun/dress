package app.myoun.dress.api.command.node.internal

import app.myoun.dress.api.command.node.AbstractCommandNode
import app.myoun.dress.api.command.node.ArgumentNode
import com.mojang.brigadier.arguments.ArgumentType

class ArgumentNodeImpl : AbstractCommandNode(), ArgumentNode {
    lateinit var argument: ArgumentType<*>

    internal fun initialize(name: String, argument: ArgumentType<*>) {
        initialize0(name)
        this.argument = argument
    }
}