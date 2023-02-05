package app.myoun.dress.api.entity.internal

import app.myoun.dress.api.entity.Player
import app.myoun.dress.fabric.server
import app.myoun.dress.fabric.util.PlayerUtil
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class PlayerImpl(override val uniqueId: UUID) : Player {
    private val minecraftPlayer: ServerPlayerEntity = server.playerManager.getPlayer(this.uniqueId) ?: throw IllegalStateException("Cannot find player of uuid $uniqueId.")

    private val audience = Audience.audience(minecraftPlayer)

    override val isOp: Boolean
        get() = PlayerUtil.hasPermission(minecraftPlayer, 4)

    override fun asAudience(): Audience = audience

    override fun sendMessage(component: Component) {
        audience.sendMessage(component)
    }
}

fun Player.Companion.fromServerPlayer(serverPlayer: ServerPlayerEntity): Player {
    return PlayerUtil.createPlayer(serverPlayer)
}