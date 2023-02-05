package app.myoun.dress.api.command

import app.myoun.dress.api.entity.Player

interface DressCommandSource {

    val sender: CommandSender

    val isPlayer: Boolean
        get() = sender is Player

    val isConsole: Boolean
        get() = sender is Console

    val isOp: Boolean

    /**
     * check vanila permission level
     */
    fun hasPermission(level: Int): Boolean

    /**
     * Fallback to requiring permission level 4 if the permission isn't set
     */
    fun hasPermission(permissionString: String, defaultLevel: Int): Boolean

    /**
     * Fallback to true if the permission isn't set
     */
    fun hasPermission(permissionString: String, default: Boolean): Boolean

}