package app.myoun.dress.api.server

import app.myoun.dress.api.entity.Player
import app.myoun.dress.api.loader.DressLoader
import java.util.UUID

interface PlayerManager {

    fun getPlayer(uniqueId: UUID): Player
    fun getPlayer(name: String): Player
    companion object : PlayerManager by DressLoader.loadImplement(PlayerManager::class.java)

}