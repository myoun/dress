@file:JvmName("FabricGlobal")
package app.myoun.dress.fabric

import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("Dress")
val server: MinecraftServer by FabricDressMod::server