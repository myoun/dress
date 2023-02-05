package app.myoun.dress.api.command

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

interface CommandSender {
    val isOp: Boolean

    fun sendMessage(component: Component)
    fun asAudience(): Audience

}