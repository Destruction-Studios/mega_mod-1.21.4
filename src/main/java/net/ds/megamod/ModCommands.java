package net.ds.megamod;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.ds.megamod.config.MegaModConfig;
import net.ds.megamod.util.Utils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Objects;

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

    private static int endermanAnger(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "target");
        boolean shouldSpawn = BoolArgumentType.getBool(context, "shouldSpawnEndermen");
        ServerCommandSource source = context.getSource();
        World world = Objects.requireNonNull(context.getSource().getPlayer()).getWorld();

        BlockPos pos = player.getBlockPos();

        int dist = MegaModConfig.getConfig().Trolls.MaxEndermanSearchRadius;

        Box box = new Box(
                pos.getX() - dist,
                pos.getY() - dist,
                pos.getZ() - dist,
                pos.getX() + dist,
                pos.getY() + dist,
                pos.getZ() + dist
        );

        if (shouldSpawn) {
            for (int i = 0; i < MegaModConfig.getConfig().Trolls.EndermanToSpawn; i++) {
                var entity = EntityType.ENDERMAN.spawn(source.getWorld(), source.getPlayer().getBlockPos(), SpawnReason.COMMAND);
                if (entity != null) {
                    entity.teleport(pos.getX(), pos.getY(), pos.getZ(), true);
                }
            }
        }

        for (EndermanEntity endermanEntity : world.getEntitiesByType(TypeFilter.instanceOf(EndermanEntity.class), box, Entity::isLiving)) {
            endermanEntity.setAngryAt(player.getUuid());
            endermanEntity.setAngerTime(6000);
        }


        sendFeedback(context,"Enderman Anger directed to "+player.getName().getString(), true);
        return 1;
    }

    private static int executeZombieTrolls(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "target");

        var TrollConfig = MegaModConfig.getConfig().Trolls;

        for (int i = 0; i < TrollConfig.BabyZombiesToSpawn; i ++) {
            var entity = EntityType.ZOMBIE.spawn(player.getWorld(), player.getBlockPos(), SpawnReason.COMMAND);
            if (entity != null) {
                entity.setCanBreakDoors(true);
                entity.setBaby(true);
                entity.age = -50000;

                if (TrollConfig.BabyZombieTotemCount > 0) {
                    ItemStack stack = new ItemStack(Items.TOTEM_OF_UNDYING);
                    stack.set(DataComponentTypes.MAX_STACK_SIZE, TrollConfig.BabyZombieTotemCount);
                    stack.setCount(TrollConfig.BabyZombieTotemCount);
                    entity.setStackInHand(Hand.MAIN_HAND, stack);
                }
            }
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

                    .then(
                            CommandManager.literal("troll").requires(source -> MegaModConfig.getConfig().Trolls.TrollCommandEnabled).requires(ServerCommandSource::isExecutedByPlayer)
                                    .then(CommandManager.literal("endermanAnger").then(CommandManager.argument("target", EntityArgumentType.player()).then(CommandManager.argument("shouldSpawnEndermen", BoolArgumentType.bool()).executes(ModCommands::endermanAnger))))
                                    .then(CommandManager.literal("babyZombies").then(CommandManager.argument("target", EntityArgumentType.player()).executes(ModCommands::executeZombieTrolls)))
                    )

            );
        }));

    }

    private static void sendFeedback(CommandContext<ServerCommandSource> context, String text, boolean broadcast) {
        context.getSource().sendFeedback(() -> Text.literal(text), broadcast);
    }
}
