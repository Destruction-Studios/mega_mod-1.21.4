package net.ds.megamod.mixin;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.ds.megamod.config.MegaModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
//    @Inject(method = "useRiptide", at = @At("HEAD"), cancellable = true)
//    public void injectUseRiptide(int riptideTicks, float riptideAttackDamage, ItemStack stack, CallbackInfo ci) {
//        if (CombatTagDataEditor.getCombat((IPlayerDataSaver) this) && MegaModConfig.getConfig().Combat.DisabledWhenInCombat.RiptideTridents) {
//            MegaMod.LOGGER.info("Trident disabled");
//            ci.cancel();
//            return;
//        }
//    }
}
