package net.ds.megamod;

import net.ds.megamod.gen.ModBlockProvider;
import net.ds.megamod.gen.ModEnchantmentProvider;
import net.ds.megamod.gen.ModEnglishLangProvider;
import net.ds.megamod.gen.ModItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MegaModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModEnchantmentProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModBlockProvider::new);
		pack.addProvider(ModEnglishLangProvider::new);
	}
}
