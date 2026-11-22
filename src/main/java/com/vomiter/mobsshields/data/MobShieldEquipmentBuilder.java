package com.vomiter.mobsshields.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

/** Builds one weighted mob_shield layer without handling file output. */
public final class MobShieldEquipmentBuilder {
    private final double spawnChance;
    private final List<ShieldEntry> shields = new ArrayList<>();
    private boolean replace;
    private int useDuration = 60;
    private int cooldownDuration = 60;
    private int checkContinueToUseInterval = 10;

    private MobShieldEquipmentBuilder(double spawnChance) {
        this.spawnChance = spawnChance;
    }

    public static MobShieldEquipmentBuilder create(double spawnChance) {
        return new MobShieldEquipmentBuilder(spawnChance);
    }

    public static MobShieldEquipmentBuilder create() {
        return new MobShieldEquipmentBuilder(-1);
    }


    public MobShieldEquipmentBuilder replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public MobShieldEquipmentBuilder useDuration(int ticks) {
        useDuration = ticks;
        return this;
    }

    public MobShieldEquipmentBuilder cooldownDuration(int ticks) {
        cooldownDuration = ticks;
        return this;
    }

    public MobShieldEquipmentBuilder checkContinueToUseInterval(int ticks) {
        checkContinueToUseInterval = ticks;
        return this;
    }

    public MobShieldEquipmentBuilder addShield(ItemLike shield, double weight, double minDifficulty) {
        shields.add(new ShieldEntry(shield.asItem(), weight, minDifficulty));
        return this;
    }

    public JsonObject build() {
        JsonObject json = new JsonObject();
        if (spawnChance != -1) json.addProperty("spawn_chance", spawnChance);
        json.addProperty("replace", replace);
        json.addProperty("use_duration", useDuration);
        json.addProperty("cooldown_duration", cooldownDuration);
        json.addProperty("check_continue_to_use_interval", checkContinueToUseInterval);

        JsonArray entries = new JsonArray();
        for (ShieldEntry shield : shields) {
            JsonObject entry = new JsonObject();
            entry.addProperty("shield_id", BuiltInRegistries.ITEM.getKey(shield.item()).toString());
            entry.addProperty("weight", shield.weight());
            entry.addProperty("min_difficulty", shield.minDifficulty());
            entries.add(entry);
        }
        json.add("shields", entries);
        return json;
    }

    private record ShieldEntry(Item item, double weight, double minDifficulty) {}
}
