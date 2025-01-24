package net.ds.megamod.config;

import net.ds.megamod.MegaMod;
import org.quiltmc.parsers.json.JsonReader;
import org.quiltmc.parsers.json.JsonWriter;

import java.io.File;
import java.io.IOException;

public class ModConfig {
    public static ModConfig.Config CONFIG;
    static File file = new File("config/megamod/config.json5");

    @SuppressWarnings({"SwitchStatementWithTooFewBranches"})
    public static Config load() {
        if (!file.getName().endsWith(".json5"))
            throw new RuntimeException("Failed to read config");
        Config cfg = null;
        if (file.exists()) {
            try (JsonReader reader = JsonReader.json5(file.toPath())) {
                cfg = new Config();
                reader.beginObject();
                while (reader.hasNext()) {
                    String nextName = reader.nextName();
                    switch (nextName) {
                        case "combatTime":
                            cfg.combatTime = reader.nextInt();
                            break;
                        case "netherPortalsEnabled":
                            cfg.netherPortalsEnabled = reader.nextBoolean();
                            break;
                        case "endPortalsEnabled":
                            cfg.endPortalsEnabled = reader.nextBoolean();
                            break;
                        case "enderEyesEnabled":
                            cfg.enderEyesEnabled = reader.nextBoolean();
                            break;
                        default:
                            reader.skipValue();
                            break;
                    }
                }
                reader.endObject();
                return cfg;
            } catch (IOException e) {
                MegaMod.LOGGER.error("Failed to parse config", e);
            }
        }
        if (cfg == null) cfg = new Config();
        save(file, cfg);
        return cfg;
    }
    public static void save(File file, Config cfg) {
        try (JsonWriter writer = JsonWriter.json5(file.toPath())) {
            writer.beginObject();
            writer.comment("The amount of time that a player is in combat. (seconds)")
                    .name("combatTime").value(cfg.combatTime);
            writer.comment("Whether nether portals can be used.")
                        .name("netherPortalsEnabled").value(cfg.netherPortalsEnabled);
            writer.comment("Whether end portals can be used or eyes can be placed on end portal frames")
                    .name("endPortalsEnabled").value(cfg.endPortalsEnabled);
            writer.comment("Whether nether portals can be used.")
                    .name("enderEyesEnabled").value(cfg.enderEyesEnabled);
            writer.endObject();
        } catch (IOException e) {
            MegaMod.LOGGER.error("Failed to save config", e);
        }
    }
    public static class Config {
        public static int combatTime = 30;
        public static boolean netherPortalsEnabled = true;
        public static boolean endPortalsEnabled = true;
        public static boolean enderEyesEnabled = true;

        public static void setCombatTime(int time) {
            combatTime = time;
        }
    }
}
