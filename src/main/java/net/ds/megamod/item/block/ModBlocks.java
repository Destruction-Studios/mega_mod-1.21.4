package net.ds.megamod.item.block;

import net.ds.megamod.MegaMod;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static Block register(Block block, RegistryKey<Block> key, boolean shouldRegister) {
//        if (shouldRegister) {
//            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, key.getValue());
//
//            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
//            Registry.register(Registries.ITEM, itemKey, blockItem);
//        }

        return Registry.register(Registries.BLOCK, key, block);
    }
    public static RegistryKey<Block> of(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MegaMod.MOD_ID, id));
    }
    public static void init() {
        MegaMod.LOGGER.info("Registering Blocks");

//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
//            fabricItemGroupEntries.add(CONDENSED_DIRT.asItem());
//        });
    }
}
