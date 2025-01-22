package net.ds.megamod.config;

import com.mojang.datafixers.util.Pair;
import net.ds.megamod.MegaMod;

import java.util.List;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean END_PORTAL_ENABLED;
    public static boolean CAN_THROW_EYE;
    public static boolean NETHER_PORTAL_ENABLED;

    public static void init() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of("megamod/config").provider(configs).request();

        assignConfigs();
    }

    public static void reloadConfig() {
        MegaMod.LOGGER.info("Reloading...");
        init();
        MegaMod.LOGGER.info("Reloaded!");
    }

    @Deprecated
    public static String getContents() {
        List<Pair> list = configs.getConfigsList();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            try {
                Pair current = list.get(i);
                MegaMod.LOGGER.info("index: {}, 1:{} 2:{}", i, current.getFirst(), current.getSecond());
                stringBuilder.append(current.getFirst()).append(" = ").append(current.getSecond());
                if (i < (list.size() - 1)) {
                    stringBuilder.append("\n");
                }
            } catch (IndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    public static String getFileContents() {
        return configs.get("null");
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("portal.end", true), "Whether End Portals can be used or if Eyes Of Ender can be placed on portal frames");
        configs.addKeyValuePair(new Pair<>("portal.nether", true), "Whether Nether Portals can be used.");
        configs.addKeyValuePair(new Pair<>("item.ender_eye", true), "Whether Eyes of Ender can be thrown.");
    }

    private static void assignConfigs() {
        END_PORTAL_ENABLED = CONFIG.getOrDefault("portal.end", true);
        NETHER_PORTAL_ENABLED = CONFIG.getOrDefault("portal.nether", true);
        CAN_THROW_EYE = CONFIG.getOrDefault("item.ender_eye", true);

        MegaMod.LOGGER.info("All {} config settings have been set", configs.getConfigsList().size());
    }
}