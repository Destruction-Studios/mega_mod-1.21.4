package net.ds.megamod;


import net.ds.megamod.config.ModConfigs;
import net.ds.megamod.event.ServerStartedEvent;
import net.ds.megamod.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MegaMod implements ModInitializer {
	public static final String MOD_ID = "megamod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static String rules;
	private static MinecraftServer server;
	private static boolean serverStartHandled = false;

	@Override
	public void onInitialize() {
		ModConfigs.init();

		ServerLifecycleEvents.SERVER_STARTED.register(new ServerStartedEvent());

		ModCommands.init();

		Utils.getOrCreateConfigFolder();

		LOGGER.info("MegaMod Initialized");
	}

	private static void handleServerStart(MinecraftServer server) {
		MegaMod.setRules(Utils.reloadRules());
		if (!server.isDedicated()) {
			MegaMod.LOGGER.info("not dedicated server");
			return;
		}
		MegaMod.setMOTD(Utils.reloadMOTD(), server);
	}


	public static void setRules(String newRules) {
		rules = newRules;
	}
	public static String getRules() {return rules;}
	public static MinecraftServer getServer(){
		return server;
	}

	public static void serverStarted(MinecraftServer newServer) {
		if (serverStartHandled) {
			return;
		}
		MegaMod.LOGGER.info("Handling Server Start...");

		serverStartHandled = true;
		server = newServer;
		handleServerStart(newServer);
	}


	public static boolean setMOTD(String MOTD, MinecraftServer server) {
        MegaMod.LOGGER.info("Setting MOTD: {}", MOTD);

		server.setMotd(MOTD);

		return true;
	}
}