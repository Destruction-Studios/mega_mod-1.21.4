package net.ds.megamod.mixin;

import net.ds.megamod.combatLog.func.CheckShouldEnterCombat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ModEntityDamageMixin extends Entity {
    public ModEntityDamageMixin(EntityType<?> type, World world) {
        super(type, world);
    }

//    @Shadow
//    public abstract boolean damage(DamageSource source, float amount);

    @Inject(method = "damage", at = @At("TAIL"))
    protected void injectCheckDamageMethod(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        CheckShouldEnterCombat.checkShouldEnterCombat(this);
    }
}
