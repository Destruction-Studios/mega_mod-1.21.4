package net.ds.megamod.event;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerStoppingEvent implements ServerLifecycleEvents.ServerStopping {
    @Override
    public void onServerStopping(MinecraftServer minecraftServer) {
        MegaMod.LOGGER.info("Server stopping, removing combat.");
        for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
            CombatTagDataEditor.removeCombat((IPlayerDataSaver) player);
        }
    }
}
