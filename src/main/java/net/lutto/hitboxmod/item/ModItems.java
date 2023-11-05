package net.lutto.hitboxmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lutto.hitboxmod.HitboxMod;
import net.lutto.hitboxmod.item.custom.HitboxTokenItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item HITBOX_TOKEN = registerItem("hitbox_token",
            new HitboxTokenItem(new FabricItemSettings().maxDamage(2)));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(HITBOX_TOKEN);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(HitboxMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        HitboxMod.LOGGER.info("Registering Mod Items for " + HitboxMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}