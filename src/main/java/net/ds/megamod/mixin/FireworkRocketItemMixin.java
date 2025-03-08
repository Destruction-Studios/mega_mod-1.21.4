package net.ds.megamod.mixin;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.ds.megamod.config.MegaModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void injectUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!MegaModConfig.getConfig().FeatureToggling.ElytraRocketsEnabled) {
            cir.setReturnValue(ActionResult.PASS);
            cir.cancel();
            return;
        }
        IPlayerDataSaver player = (IPlayerDataSaver) user;
        if (CombatTagDataEditor.getCombat(player) && MegaModConfig.getConfig().Combat.DisabledWhenInCombat.ElytraRockets) {
            cir.setReturnValue(ActionResult.PASS);
            cir.cancel();
            return;
        }
    }
}
