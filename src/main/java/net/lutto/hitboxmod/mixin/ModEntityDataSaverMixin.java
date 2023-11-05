package net.lutto.hitboxmod.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.lutto.hitboxmod.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {

    private NbtCompound persistentData;

    private final String FILE_NAME = "hitbox_data.dat";
    private final Path savePath = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {
        if(persistentData != null) {
            nbt.put("tutorialmod.lutto_data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("hitboxmod.lutto_data", 10)) {
            persistentData = nbt.getCompound("hitboxmod.lutto_data");
        }
    }

    private void savePersistentDataToFile() {
        try {
            Files.write(savePath, persistentData.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void loadPersistentDataFromFile() {
        if (Files.exists(savePath)) {
            try {
                String data = new String(Files.readAllBytes(savePath));
                persistentData = StringNbtReader.parse(data);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
