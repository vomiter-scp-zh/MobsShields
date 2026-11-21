package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.common.loot.AddShieldLootModifier;
import com.vomiter.mobsshields.registry.ShieldItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public final class ShieldChestLoot extends GlobalLootModifierProvider {
    public ShieldChestLoot(PackOutput output) {
        super(output, MobsShields.MOD_ID);
    }

    @Override
    protected void start() {
        chest(BuiltInLootTables.BASTION_HOGLIN_STABLE, ShieldItems.HOGLIN_SHIELD.get(), 0.20F);
        chest(BuiltInLootTables.BASTION_OTHER, ShieldItems.HOGLIN_SHIELD.get(), 0.10F);
        chest(BuiltInLootTables.SHIPWRECK_TREASURE, ShieldItems.SQUID_SHIELD.get(), 0.15F);
        chest(BuiltInLootTables.UNDERWATER_RUIN_BIG, ShieldItems.SQUID_SHIELD.get(), 0.10F);
    }

    private void chest(ResourceLocation table, Item shield, float chance) {
        add(table.getPath(), new AddShieldLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(table).build(),
                LootItemRandomChanceCondition.randomChance(chance).build()
        }, shield));
    }
}
