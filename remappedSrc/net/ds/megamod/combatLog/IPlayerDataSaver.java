package net.ds.megamod.combatLog;

import net.minecraft.nbt.NbtCompound;

public interface IPlayerDataSaver {
    NbtCompound getPersistentData();
}
