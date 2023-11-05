package net.lutto.hitboxmod.item.custom;

import net.lutto.hitboxmod.util.HitboxSizeData;
import net.lutto.hitboxmod.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class HitboxTokenItem extends Item {
    public HitboxTokenItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient()) {
            user.sendMessage(Text.literal("started"));

            HitboxSizeData.addHitbox((IEntityDataSaver) user, 1);
            user.sendMessage(Text .literal("Hitbox: " + ((IEntityDataSaver) user).getPersistentData().getInt("hitbox")), true);
            user.sendMessage(Text.literal("finished (hopefully correctly)"));
        } else {
            System.out.println("Not client!");
        }

        return TypedActionResult.success(getDefaultStack());
    }
}
