package net.ds.megamod;

import net.ds.megamod.effect.ModEffects;
import net.ds.megamod.enchantment.ModEnchantmentEffects;
import net.ds.megamod.item.ModItems;
import net.ds.megamod.item.ModPotions;
import net.ds.megamod.item.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MegaMod implements ModInitializer {
	public static final String MOD_ID = "megamod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing MegaMod...");

		ModItems.init();
		ModEnchantmentEffects.init();
		ModEffects.init();
		ModPotions.init();
		ModBlocks.init();

		LOGGER.info("Initialized MegaMod!");
	}
}