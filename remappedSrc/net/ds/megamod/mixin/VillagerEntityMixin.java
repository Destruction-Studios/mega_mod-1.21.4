package net.ds.megamod.mixin;

import net.ds.megamod.MegaMod;
import net.ds.megamod.config.MegaModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @Shadow public abstract VillagerData getVillagerData();

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void injectVillagerOnDeath(DamageSource damageSource, CallbackInfo ci) {
        if (!MegaModConfig.getConfig().VillagerDeathMessages) {
            return;
        }
        String profession = this.getVillagerData().getProfession().toString();
//        if (!Objects.equals(profession, "none")) {
            String output = damageSource.getDeathMessage((LivingEntity) (Object) this).getString();
            MegaMod.getServer().getPlayerManager().broadcast(Text.of(output), false);
//        }
    }
}
