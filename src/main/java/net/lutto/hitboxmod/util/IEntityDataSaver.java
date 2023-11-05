package net.lutto.hitboxmod.util;

import net.minecraft.nbt.NbtCompound;
public interface IEntityDataSaver {
    String getEntityName(); // Define a method to retrieve the player's name
    NbtCompound getPersistentData(); // Define a method to retrieve persistent data for the entity
    // Other methods relevant to entity data handling
}