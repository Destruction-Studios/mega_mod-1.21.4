package net.ds.megamod.event;

import net.ds.megamod.MegaMod;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class ServerStartedEvent implements ServerLifecycleEvents.ServerStarted{
    @Override
    public void onServerStarted(MinecraftServer minecraftServer) {
        MegaMod.serverStarted(minecraftServer);
    }
}
