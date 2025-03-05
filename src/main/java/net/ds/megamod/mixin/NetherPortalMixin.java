package net.ds.megamod.mixin;

import net.ds.megamod.config.MegaModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherPortalBlock.class)
public class NetherPortalMixin {
    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    protected void injectOnEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!MegaModConfig.getConfig().FeatureToggling.NetherEnabled) {
            ci.cancel();
            return;
        }
    }
}
