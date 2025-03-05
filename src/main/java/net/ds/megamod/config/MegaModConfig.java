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
    public static final int CONFIG_VERSION = 1;

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
//        public String _c1 = "Must restart server for max lead distance to update";
//        @InRangeFloat(min = 5.0f, max = 150.0f)
//        public float MaxLeadDistance = 10;
        public CombatConfig Combat = new CombatConfig();
        public FeatureToggle FeatureToggling = new FeatureToggle();

        public static class CombatConfig {
            @InRangeInt(min = 1, max = 1000)
            public int CombatDuration = 20;
            public List<String> CombatTriggerEntities = List.of("player");

            public CombatDisabledFeatures DisabledWhenInCombat = new CombatDisabledFeatures();
        }
        public static class CombatDisabledFeatures {
//            public boolean RiptideTridents = false;
            public boolean ElytraRockets = false;
        }
        public static class FeatureToggle {
            public boolean NetherEnabled = true;
            public boolean EndEnabled = true;
            public boolean EnderEyesEnabled = true;
            public boolean ElytraRocketsEnabled = true;
        }
    }
}
