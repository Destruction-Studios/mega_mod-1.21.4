package net.ds.megamod.mixin;

import net.ds.megamod.config.ModConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EndPortalBlock.class)
public class EndPortalMixin {
    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    protected void injectEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!ModConfigs.END_PORTAL_ENABLED) {
            ci.cancel();
            return;
        }
    }

}
