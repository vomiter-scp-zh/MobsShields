package com.vomiter.mobsshields.data;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/** MobsUseShields data uses the vanilla entity namespace for its equipment profiles. */
public final class ShieldMobEquipment implements DataProvider {
    private final Path output;

    public ShieldMobEquipment(PackOutput output) {
        this.output = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve("minecraft/mob_shield/drowned.json");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        JsonObject json = new JsonObject();
        json.addProperty("use_duration", 60);
        json.addProperty("cooldown_duration", 60);
        json.addProperty("check_continue_to_use_interval", 10);
        json.addProperty("shield_id", "mobsshields:squid_shield");
        json.addProperty("chance", 0.25);
        json.addProperty("min_difficulty", 0);
        return DataProvider.saveStable(cache, json, output);
    }

    @Override
    public String getName() { return "Squid shield drowned equipment"; }
}
