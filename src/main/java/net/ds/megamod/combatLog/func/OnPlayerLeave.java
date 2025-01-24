package net.ds.megamod.combatLog.func;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

import java.util.Objects;

public class OnPlayerLeave {
    public static void onPlayerDisconnect(ServerPlayerEntity player) {
        if (CombatTagDataEditor.getCombat((IPlayerDataSaver) player)) {
//            Text deathMessage = Text.of(player.getName().toString()+" §ccombat logged while while attacking§r "+Objects.requireNonNull(player.getAttacker()).getName().toString());
            Text deathMessage = Text.of(player.getDisplayName().getString()+" §c combat logged§r");
            GameRules.BooleanRule gamerule = Objects.requireNonNull(player.getServerWorld().getGameRules().get(GameRules.SHOW_DEATH_MESSAGES));

            gamerule.set(false, player.getServer());
            player.damage(player.getServerWorld(), player.getDamageSources().outOfWorld(), 10000);
            gamerule.set(true, player.getServer());

            Objects.requireNonNull(player.getServer()).getPlayerManager().broadcast(deathMessage, false);
        }
        CombatTagDataEditor.removeCombat((IPlayerDataSaver) player);
    }
}
