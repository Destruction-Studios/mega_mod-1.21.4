package net.ds.megamod.mixin;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.ds.megamod.config.MegaModConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Inject(method = "onStoppedUsing", at = @At("HEAD"), cancellable = true)
    public void injectOnStopUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
        if (user instanceof PlayerEntity playerEntity) {
            MegaMod.LOGGER.info("Used trident");
            float f = EnchantmentHelper.getTridentSpinAttackStrength(stack, playerEntity);
            if ((f > 0.0F && !playerEntity.isTouchingWaterOrRain()) || (CombatTagDataEditor.getCombat((IPlayerDataSaver) playerEntity) && MegaModConfig.getConfig().Combat.DisabledWhenInCombat.RiptideTridents)) {
                MegaMod.LOGGER.info("Trident Disabled returning");
                cir.setReturnValue(false);
                cir.cancel();
                return;
            }
        }
    }
}
