package net.ds.megamod.item;


import net.ds.megamod.MegaMod;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    private static RegistryKey<Item> createKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MegaMod.MOD_ID, id));
    };
    public static Item register(Item item, RegistryKey<Item> key) {
        return Registry.register(Registries.ITEM, key.getValue(), item);
    }
    public static void init() {
        MegaMod.LOGGER.info("Registering Items");

    }
}
