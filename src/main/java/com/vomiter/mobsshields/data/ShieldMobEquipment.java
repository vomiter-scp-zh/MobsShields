package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.registry.ShieldItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public final class ShieldMobEquipment implements DataProvider {
    private final PackOutput output;

    public ShieldMobEquipment(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(
                save(cache, EntityType.WITHER_SKELETON, MobShieldEquipmentBuilder.create()
                        .addShield(ShieldItems.WITHER_RIB_SHIELD.get(), 0.5, 2.5)),
                save(cache, EntityType.DROWNED, MobShieldEquipmentBuilder.create(0.3)
                        .addShield(ShieldItems.SQUID_SHIELD.get(), 1, 1.5)),
                save(cache, EntityType.ZOMBIE, MobShieldEquipmentBuilder.create()
                        .addShield(ShieldItems.ROTTEN_FLESH_SHIELD.get(), 1, 2)),
                save(cache, EntityType.PIGLIN, MobShieldEquipmentBuilder.create()
                        .addShield(ShieldItems.HOGLIN_SHIELD.get(), 1, 2.5)),
                save(cache, EntityType.PIGLIN_BRUTE, MobShieldEquipmentBuilder.create(0.1)
                        .addShield(ShieldItems.HOGLIN_SHIELD.get(), 1, 2)
                        .addShield(ShieldItems.GOLDEN_HOGLIN_SHIELD.get(), 0.5, 3))
        );
    }

    private CompletableFuture<?> save(CachedOutput cache, EntityType<?> mob, MobShieldEquipmentBuilder builder) {
        ResourceLocation entityTypeId = BuiltInRegistries.ENTITY_TYPE.getKey(mob);
        PackOutput.PathProvider paths = output.createPathProvider(PackOutput.Target.DATA_PACK, "mob_shield");
        return DataProvider.saveStable(cache, builder.build(), paths.json(entityTypeId));
    }

    @Override
    public String getName() { return "Mob shield equipment"; }
}
