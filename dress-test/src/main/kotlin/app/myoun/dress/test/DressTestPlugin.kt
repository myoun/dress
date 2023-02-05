package app.myoun.dress.test

import app.myoun.dress.api.command.DressCommand
import app.myoun.dress.api.command.get
import app.myoun.dress.api.plugin.DressPlugin
import com.mojang.brigadier.arguments.BoolArgumentType
import net.kyori.adventure.text.Component

class DressTestPlugin : DressPlugin() {

    override suspend fun onEnable() {
        logger.info("Hello world!")
    }

    override suspend fun onDisable() {
        logger.info("Bye world!")
    }

    override fun onLoad() {
        DressCommand.register("bye") {
            then("isHappy" to BoolArgumentType.bool()) {
                executes {
                    logger.info("why...")
                    val isHappy: Boolean = it["isHappy"]
                    logger.info(isHappy.toString())
                }
            }
        }
    }
}