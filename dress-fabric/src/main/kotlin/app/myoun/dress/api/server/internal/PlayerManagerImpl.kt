package app.myoun.dress.api.server.internal

import app.myoun.dress.api.entity.Player
import app.myoun.dress.api.entity.internal.PlayerImpl
import app.myoun.dress.api.entity.internal.fromServerPlayer
import app.myoun.dress.api.server.PlayerManager
import app.myoun.dress.fabric.server
import java.util.*

class PlayerManagerImpl : PlayerManager {

    override fun getPlayer(uniqueId: UUID): Player {
        return PlayerImpl(uniqueId)
    }

    override fun getPlayer(name: String): Player {
        return Player.fromServerPlayer(server.playerManager.getPlayer(name) ?: throw IllegalStateException("Cannot find player of name $name"))
    }

}