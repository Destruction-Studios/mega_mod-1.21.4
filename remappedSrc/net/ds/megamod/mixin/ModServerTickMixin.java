package net.ds.megamod.mixin;

import net.ds.megamod.combatLog.func.OnWorldTick;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class ModServerTickMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        OnWorldTick.onWorldTick((MinecraftServer) (Object) this);
    }
}
