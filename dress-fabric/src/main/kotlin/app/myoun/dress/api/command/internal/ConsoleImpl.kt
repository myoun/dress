package app.myoun.dress.api.command.internal

import app.myoun.dress.api.command.Console
import app.myoun.dress.fabric.FabricDressMod
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.minecraft.server.MinecraftServer

class ConsoleImpl : Console {
    private val server: MinecraftServer
        get() = FabricDressMod.server
    private val audience: Audience = Audience.audience(server)

    override val isOp: Boolean
        get() = true

    override fun asAudience(): Audience = audience

    override fun sendMessage(component: Component) {
        audience.sendMessage(component)
    }

}