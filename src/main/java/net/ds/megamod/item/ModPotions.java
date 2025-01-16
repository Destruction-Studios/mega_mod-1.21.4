package net.ds.megamod.item;

import net.ds.megamod.MegaMod;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;

public class ModPotions {

    public static void init() {
        MegaMod.LOGGER.info("Registering Potions");

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {

        });
    }
}
