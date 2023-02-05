package app.myoun.dress.fabric.util;

import app.myoun.dress.api.entity.internal.PlayerImpl;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class PlayerUtil {

    public static Boolean hasPermission(ServerPlayerEntity player, int level) {
        return player.hasPermissionLevel(level);
    }

    public static PlayerImpl createPlayer(ServerPlayerEntity player) {
        return new PlayerImpl(player.getUuid());
    }

    public static UUID getUUID(ServerPlayerEntity player) {
        return player.getUuid();
    }
}
