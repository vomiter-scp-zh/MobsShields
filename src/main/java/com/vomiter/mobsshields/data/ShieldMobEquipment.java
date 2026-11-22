package com.vomiter.mobsshields.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vomiter.mobsshields.registry.ShieldItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class ShieldMobEquipment implements DataProvider {
    private final PackOutput output;

    public ShieldMobEquipment(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(
                profile(cache, EntityType.WITHER_SKELETON, ShieldItems.WITHER_RIB_SHIELD.get(), 0.1f),
                profile(cache, EntityType.DROWNED, ShieldItems.SQUID_SHIELD.get(), 0.3f),
                profile(cache, EntityType.ZOMBIE, ShieldItems.ROTTEN_FLESH_SHIELD.get(), 0.05f),
                profile(cache, EntityType.PIGLIN, ShieldItems.HOGLIN_SHIELD.get(), 0.15f),
                profile(cache, EntityType.PIGLIN_BRUTE, ShieldItems.GOLDEN_HOGLIN_SHIELD.get(), 0.1f)
        );
    }

    private CompletableFuture<?> profile(CachedOutput cache, EntityType<?> mob, Item shield, float chance) {
        JsonObject json = new JsonObject();
        ResourceLocation entityTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(mob);
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(shield);

        json.addProperty("use_duration", 60);
        json.addProperty("cooldown_duration", 60);
        json.addProperty("check_continue_to_use_interval", 10);
        json.addProperty("spawn_chance", chance);
        json.addProperty("replace", false);
        JsonObject entry = new JsonObject();
        entry.addProperty("shield_id", itemId.toString());
        entry.addProperty("weight", 1);
        entry.addProperty("min_difficulty", 2.5);
        JsonArray shields = new JsonArray();
        shields.add(entry);
        json.add("shields", shields);

        Path outputPath = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve( entityTypeId.getNamespace() + "/mob_shield");

        return DataProvider.saveStable(cache, json, outputPath.resolve(entityTypeId.getPath() + ".json"));
    }

    @Override
    public String getName() { return "Mob shield equipment"; }
}
