package net.ds.megamod.combatLog.func;

import net.ds.megamod.MegaMod;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.ds.megamod.util.Utils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Optional;

public class OnWorldTick {
    public static void onWorldTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            Optional<Integer> combatTime = CombatTagDataEditor.getCombatTime((IPlayerDataSaver) player);
            Optional<Boolean> inCombat =CombatTagDataEditor.getCombat((IPlayerDataSaver) player);
            if (combatTime.isPresent() && inCombat.isPresent()) {
                int value = combatTime.get();
                if (value > 0) {
                    CombatTagDataEditor.decreaseCombatTime((IPlayerDataSaver) player);
                    player.sendMessage(Text.literal("You are in combat do not leave! ("+ Utils.tickToString(value)+")").fillStyle(Style.EMPTY.withBold(true).withColor(Formatting.RED)), true);
                } else if (inCombat.get()) {
                    CombatTagDataEditor.removeCombat((IPlayerDataSaver) player);
                    player.sendMessage(Text.literal("You are no longer in combat.").fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), true);
                }
            }
        }
    }
}
