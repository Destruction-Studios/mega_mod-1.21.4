package net.ds.megamod.item.armor;

import net.ds.megamod.MegaMod;
import net.ds.megamod.gen.ModItemTagProvider;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ExampleArmorMaterial {
    public static final int BASE_DURABILITY = 15;
    public static final RegistryKey<EquipmentAsset> EXAMPLE_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MegaMod.MOD_ID, "guidite"));

//    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
//            BASE_DURABILITY,
//            Map.of(
//                    EquipmentType.HELMET, 3,
//                    EquipmentType.CHESTPLATE, 8,
//                    EquipmentType.LEGGINGS, 6,
//                    EquipmentType.BOOTS, 3
//            ),
//            5,
//            SoundEvents.ITEM_ARMOR_EQUIP_TURTLE,
//            0F,
//            0F,
//            KEY,
//            EXAMPLE_ARMOR_MATERIAL_KEY
//    );
}
