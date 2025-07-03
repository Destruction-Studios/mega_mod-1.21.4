package net.ds.megamod.config;

import com.google.gson.JsonObject;
import net.ds.megamod.MegaMod;
import ru.nern.fconfiglib.v1.ConfigManager;
import ru.nern.fconfiglib.v1.api.annotations.restrictions.InRangeFloat;
import ru.nern.fconfiglib.v1.api.annotations.restrictions.InRangeInt;
import ru.nern.fconfiglib.v1.json.JsonConfigManager;
import ru.nern.fconfiglib.v1.log.Sl4jLoggerWrapper;

import java.util.List;


public class MegaModConfig {
    public static final int CONFIG_VERSION = 5;

    public static ConfigManager<Config, JsonObject> manager = JsonConfigManager
            .builderOf(Config.class)
            .modId(MegaMod.MOD_ID)
            .version(CONFIG_VERSION)
            .logger(Sl4jLoggerWrapper.createFrom(MegaMod.LOGGER))
            .create();

    public static void init() {
        manager.init();
    }

    public static Config getConfig() {
        return manager.config();
    }

    public static class Config {
        public boolean VillagerDeathMessages = true;
        public FeatureToggle FeatureToggling = new FeatureToggle();
        public Trolls Trolls = new Trolls();

        public static class FeatureToggle {
            public boolean NetherEnabled = true;
            public boolean EndEnabled = true;
            public boolean EnderEyesEnabled = true;
            public boolean ElytraRocketsEnabled = true;
        }
        public static class Trolls {
            public boolean TrollCommandEnabled = false;
            public int MaxEndermanSearchRadius = 75;
            public int EndermanToSpawn = 0;
            public int BabyZombiesToSpawn = 2;
            public int BabyZombieTotemCount = 0;
//            public String _c2 = "Give command syntax (minecraft:stick[enchantments:{levels:{knockback:255}}])";
//            public String BabyZombieWeapon = "";
        }
    }
}
