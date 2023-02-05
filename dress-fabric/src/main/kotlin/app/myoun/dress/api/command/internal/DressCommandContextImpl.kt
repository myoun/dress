package app.myoun.dress.api.command.internal

import app.myoun.dress.api.command.DressCommandContext
import com.mojang.brigadier.context.CommandContext

class DressCommandContextImpl(override val input: String, val originalContext: CommandContext<*>) : DressCommandContext {
    override fun <T> get(name: String, clazz: Class<T>): T {
        return originalContext.getArgument(name, clazz)
    }
}