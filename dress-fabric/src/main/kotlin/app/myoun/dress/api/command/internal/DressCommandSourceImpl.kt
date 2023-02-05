package app.myoun.dress.api.command.internal

import app.myoun.dress.api.command.CommandSender
import app.myoun.dress.api.command.Console
import app.myoun.dress.api.command.DressCommandSource
import app.myoun.dress.api.entity.Player
import app.myoun.dress.api.server.PlayerManager
import app.myoun.dress.fabric.server
import app.myoun.dress.fabric.util.PlayerUtil
import me.lucko.fabric.api.permissions.v0.Permissions
import net.minecraft.server.command.ServerCommandSource

class DressCommandSourceImpl(
    override val sender: CommandSender
) : DressCommandSource {

    override val isOp: Boolean
        get() = sender.isOp

    override fun hasPermission(level: Int): Boolean {
        return when (sender) {
            is Console -> true
            is Player -> {
                val p = server.playerManager.getPlayer(sender.uniqueId)
                PlayerUtil.hasPermission(p, 4) ?: false
            }
            else -> false
        }
    }

    override fun hasPermission(permissionString: String, defaultLevel: Int): Boolean {
        return when (sender) {
            is Console -> true
            is Player -> {
                val p = server.playerManager.getPlayer(sender.uniqueId) ?: return false
                Permissions.check(p, permissionString, defaultLevel)
            }
            else -> false
        }
    }

    override fun hasPermission(permissionString: String, default: Boolean): Boolean {
        return when (sender) {
            is Console -> true
            is Player -> {
                val p = server.playerManager.getPlayer(sender.uniqueId) ?: return false
                Permissions.check(p, permissionString, default)
            }
            else -> false
        }
    }
}

fun ServerCommandSource.toDressCommandSource(): DressCommandSource {
    return DressCommandSourceImpl(if (isExecutedByPlayer) PlayerManager.getPlayer(PlayerUtil.getUUID(player)) else Console)
}