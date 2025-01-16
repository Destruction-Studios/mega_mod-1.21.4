package net.ds.megamod.effect;

import net.ds.megamod.MegaMod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;

public class ModEffects {

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MegaMod.MOD_ID, id), statusEffect);
    }

    public static void init() {
        MegaMod.LOGGER.info("Registering Effects");
    }
}
