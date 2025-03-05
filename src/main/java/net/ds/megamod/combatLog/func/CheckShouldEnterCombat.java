package net.ds.megamod.combatLog.func;

import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.ds.megamod.config.MegaModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Objects;

public class CheckShouldEnterCombat {
    public static void checkShouldEnterCombat(Entity entity) {
        LivingEntity target = (LivingEntity) entity;
        LivingEntity attacker = target.getAttacker();

        if (attacker == null) return;

        if (target instanceof PlayerEntity) {
            String attackerId = attacker.getType().getUntranslatedName();
            if (!MegaModConfig.getConfig().Combat.CombatTriggerEntities.contains(attackerId)) {
                return;
            }
            if (target instanceof PlayerEntity) {
                CombatTagDataEditor.placeInCombat((IPlayerDataSaver) target);
                CombatTagDataEditor.setLastAttacker((IPlayerDataSaver) target, Objects.requireNonNull(attacker.getDisplayName()).getString());
            }
            if (attacker instanceof PlayerEntity) {
                CombatTagDataEditor.setLastAttacker((IPlayerDataSaver) attacker, Objects.requireNonNull(target.getDisplayName()).getString());
                CombatTagDataEditor.placeInCombat((IPlayerDataSaver) attacker);
            }
        }
    }
}
