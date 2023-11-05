package net.lutto.hitboxmod.util;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import net.minecraft.nbt.NbtCompound;
import org.bson.Document;
import org.bson.conversions.Bson;

public class HitboxSizeData {

    public static int addHitbox(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int hitbox = nbt.getInt("hitbox");

        hitbox++;

        try {
            MongoClients.create("mongodb+srv://hitboxsmp:welcomeMonkee13@hitboxsmp.gydketv.mongodb.net/?retryWrites=true&w=majority");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Player:" + player.getEntityName());

        try {
            MongoClient mongoClient = MongoClients.create("mongodb+srv://hitboxsmp:welcomeMonkee13@hitboxsmp.gydketv.mongodb.net/?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("HitboxSMP");
            MongoCollection<Document> collection = database.getCollection("HitboxSize");

            Bson filter = Filters.eq("playerName", player.getEntityName()); // Assuming player has a getName or similar method
            Document playerData = collection.find(filter).first();

            if(playerData != null) {
                int currentHitbox = playerData.getInteger("hitbox");
                int newHitbox = currentHitbox + amount;

                Bson update = Updates.set("hitbox", newHitbox);
                collection.updateOne(filter, update);
            } else {
                Document newPlayerData = new Document("playerName", player.getEntityName())
                        .append("hitbox", amount);

                collection.insertOne(newPlayerData);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        nbt.putInt("hitbox", hitbox);

        return hitbox;
    }

}
