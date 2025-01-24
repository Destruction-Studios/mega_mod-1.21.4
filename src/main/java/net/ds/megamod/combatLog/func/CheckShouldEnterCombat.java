package net.ds.megamod.combatLog.func;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class CheckShouldEnterCombat {
    public static void checkShouldEnterCombat(Entity entity) {
        LivingEntity target = (LivingEntity) entity;
        if (target instanceof PlayerEntity) {
            if ((target.getAttacker() instanceof PlayerEntity) && (((ServerPlayerEntity) target).interactionManager.getGameMode().isSurvivalLike() && ((ServerPlayerEntity) Objects.requireNonNull(target.getAttacker())).interactionManager.getGameMode().isSurvivalLike())) {
                MegaMod.LOGGER.info("Placing into combat");
                CombatTagDataEditor.placeInCombat((IPlayerDataSaver) target);
                CombatTagDataEditor.placeInCombat((IPlayerDataSaver) target.getAttacker());
            }
        }
    }
}
