package net.lutto.hitboxmod;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.lutto.hitboxmod.item.ModItems;

public class HitboxMod implements ModInitializer {
	public static final String MOD_ID = "hitboxmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}