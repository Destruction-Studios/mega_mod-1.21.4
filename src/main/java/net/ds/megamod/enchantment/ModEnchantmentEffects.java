package net.ds.megamod.enchantment;

import com.mojang.serialization.MapCodec;
import net.ds.megamod.MegaMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    private static RegistryKey<Enchantment> of (String path) {
        Identifier id = Identifier.of(MegaMod.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }
    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MegaMod.MOD_ID, id), codec);
    }

    public static void init() {
        MegaMod.LOGGER.info("Registering Enchants");
    }
}
