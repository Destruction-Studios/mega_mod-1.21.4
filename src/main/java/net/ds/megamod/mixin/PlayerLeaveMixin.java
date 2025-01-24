package net.ds.megamod.mixin;

import net.ds.megamod.combatLog.func.OnPlayerLeave;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class PlayerLeaveMixin {
    @Inject(method = "onDisconnect", at = @At("HEAD"))
    private void injectDisconnectMethod(CallbackInfo ci) {
        OnPlayerLeave.onPlayerDisconnect((ServerPlayerEntity) (Object) this);
    }
}
