package net.ds.megamod.combatLog;

import net.ds.megamod.config.MegaModConfig;
import net.minecraft.nbt.NbtCompound;

public class CombatTagDataEditor {
    public static void setValues(IPlayerDataSaver playerDataSaver, int combatTime, boolean inCombat) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        nbt.putInt("combatTime", combatTime);
        nbt.putBoolean("inCombat", inCombat);
    }

    public static void decreaseCombatTime(IPlayerDataSaver playerDataSaver) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        int combatTime = nbt.getInt("combatTime");
        if (combatTime > 0) {
            combatTime--;
            nbt.putInt("combatTime", combatTime);
        }
    }

    public static void placeInCombat(IPlayerDataSaver playerDataSaver) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        nbt.putInt("combatTime", MegaModConfig.getConfig().Combat.CombatDuration * 20);
        nbt.putBoolean("inCombat", true);
    }

    public static void removeCombat(IPlayerDataSaver playerDataSaver) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        nbt.putInt("combatTime", 0);
        nbt.putBoolean("inCombat", false);
    }

    public static void setLastAttacker(IPlayerDataSaver playerDataSaver, String lastAttacker) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        nbt.putString("lastAttacker", lastAttacker);
    }

    public static int getCombatTime(IPlayerDataSaver playerDataSaver) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        return nbt.getInt("combatTime");
    }

    public static boolean getCombat(IPlayerDataSaver playerDataSaver) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        return nbt.getBoolean("inCombat");
    }

    public static String getLastAttacker(IPlayerDataSaver playerDataSaver) {
        NbtCompound nbt = playerDataSaver.getPersistentData();
        return nbt.getString("lastAttacker");
    }
}
