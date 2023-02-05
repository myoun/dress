package app.myoun.dress.api.command.node

import app.myoun.dress.api.command.DressCommandContext
import app.myoun.dress.api.command.DressCommandDsl
import app.myoun.dress.api.command.DressCommandSource
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext

@DressCommandDsl
interface DressCommandNode {

    var permission: String?

    fun requires(requires: DressCommandSource.() -> Boolean)

    fun executes(executes: DressCommandSource.(DressCommandContext) -> Unit)

    fun then(
        argument: Pair<String, ArgumentType<*>>,
        vararg arguments: Pair<String, ArgumentType<*>>,
         init: DressCommandNode.() -> Unit
    )

    fun then(
        name: String,
        vararg arguments: Pair<String, ArgumentType<*>>,
        init: DressCommandNode.() -> Unit
    )




}