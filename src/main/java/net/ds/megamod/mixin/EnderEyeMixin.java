package net.ds.megamod.mixin;

import net.ds.megamod.config.ModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
public class EnderEyeMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void injectUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!ModConfig.Config.enderEyesEnabled) {
            cir.setReturnValue(ActionResult.PASS);
            cir.cancel();
            return;
        }
    }
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void injectUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!ModConfig.Config.endPortalsEnabled) {
            cir.setReturnValue(ActionResult.PASS);
            cir.cancel();
            return;
        }
    }
}
