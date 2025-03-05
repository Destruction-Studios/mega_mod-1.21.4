package net.ds.megamod;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ds.megamod.combatLog.IPlayerDataSaver;
import net.ds.megamod.combatLog.CombatTagDataEditor;
import net.ds.megamod.config.MegaModConfig;
import net.ds.megamod.util.Utils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class ModCommands {
    private static int executeRulesCommand(CommandContext<ServerCommandSource> context) {
        sendFeedback(context, MegaMod.getRules(), false);
        return 1;
    };
    private static int executeReloadCommand(CommandContext<ServerCommandSource> context) {
        MegaMod.setRules(Utils.reloadRules());
        context.getSource().sendFeedback(() -> Text.literal("Reloaded Rules!"), true);
        return 1;
    }

    private static int executeReloadMOTD(CommandContext<ServerCommandSource> context) {
        if(context.getSource().getServer().isDedicated()) {
            sendFeedback(context, "Reloading MOTD...", true);

            boolean success = MegaMod.setMOTD(Utils.reloadMOTD(), context.getSource().getServer());

            if (success) {
                sendFeedback(context, "Successfully reloaded MOTD!", true);
                return 1;
            } else {
                sendFeedback(context, "Failed to reload MOTD", true);
                return 0;
            }
        } else {
            sendFeedback(context, "Unable to reload MOTD, not dedicated server", false);
            return -1;
        }
    }

    private static int executeGetMOTD(CommandContext<ServerCommandSource> context) {
        if (context.getSource().getServer().isDedicated()) {
            sendFeedback(context, context.getSource().getServer().getServerMotd(), false);
            return 1;
        } else {
            sendFeedback(context, "Unable to get MOTD, not dedicated server", false);
            return -1;
        }
    }

    private static int executePvpToggle(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        boolean pvpEnabled = BoolArgumentType.getBool(context, "enabled");

        source.getServer().setPvpEnabled(pvpEnabled);

        sendFeedback(context, "Set PVP enabled to "+pvpEnabled, true);

        return 1;
    }

    private static int executeReloadConfig(CommandContext<ServerCommandSource> context) {
        MegaModConfig.manager.load();

        sendFeedback(context, "Reloaded Config!", true);
        return 1;
    }

    private static int executeSetCombat(CommandContext<ServerCommandSource> context) {
        try {
            boolean combatEnabled = BoolArgumentType.getBool(context, "enabled");
            Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "players");
            for (ServerPlayerEntity player : players) {
                sendFeedback(context, "Set combat for " + player.getDisplayName().getString() + " to " + combatEnabled, true);
                if (combatEnabled) {
                    CombatTagDataEditor.placeInCombat((IPlayerDataSaver) player);
                    CombatTagDataEditor.setLastAttacker((IPlayerDataSaver) player, "Console");
                } else {
                    CombatTagDataEditor.setValues((IPlayerDataSaver) player, 0, true);
                }
            }
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    private static int executeGetCombat(CommandContext<ServerCommandSource> context) {
        try {
            ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("In Combat: §a").append(CombatTagDataEditor.getCombat((IPlayerDataSaver) player)).append("§r\n");
            stringBuilder.append("Combat Time: §c").append(Utils.tickToString(CombatTagDataEditor.getCombatTime((IPlayerDataSaver) player))).append("§r\n");
            stringBuilder.append("Last Attacker: §9").append(CombatTagDataEditor.getLastAttacker((IPlayerDataSaver) player)).append("§r");

            sendFeedback(context, stringBuilder.toString(), false);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register(((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(CommandManager.literal("rules")
                    .requires(ServerCommandSource::isExecutedByPlayer)
                    .executes(ModCommands::executeRulesCommand));

            commandDispatcher.register(CommandManager.literal("megamod")
                    .requires(source -> source.hasPermissionLevel(4))
                    .then(CommandManager.literal("combat")
                            .then(CommandManager.literal("set").then(CommandManager.argument("players", EntityArgumentType.players()).then(CommandManager.argument("enabled", BoolArgumentType.bool()).executes(ModCommands::executeSetCombat))))
                            .then(CommandManager.literal("get").then(CommandManager.argument("player", EntityArgumentType.player()).executes(ModCommands::executeGetCombat)))
                    )
                    .then(CommandManager.literal("motd")
                            .then(CommandManager.literal("reload").executes(ModCommands::executeReloadMOTD))
                            .then(CommandManager.literal("get").executes(ModCommands::executeGetMOTD))
                    )
                    .then(CommandManager.literal("rules").then(CommandManager.literal("reload").executes(ModCommands::executeReloadCommand)))
                    .then(CommandManager.literal("pvp").then(CommandManager.argument("enabled", BoolArgumentType.bool()).executes(ModCommands::executePvpToggle)))
                    .then(CommandManager.literal("config")
                            .then(CommandManager.literal("reload").executes(ModCommands::executeReloadConfig))
//                            .then(CommandManager.literal("get").executes(ModCommands::executeGetConfig))
                    )

            );
        }));

    }

    private static void sendFeedback(CommandContext<ServerCommandSource> context, String text, boolean broadcast) {
        context.getSource().sendFeedback(() -> Text.literal(text), broadcast);
    }
}
