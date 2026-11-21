package com.vomiter.mobsshields.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.vomiter.mobsshields.MobsShields;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AddShieldLootModifier extends LootModifier {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MobsShields.MOD_ID);
    private static final RegistryObject<Codec<AddShieldLootModifier>> CODEC = SERIALIZERS.register("add_shield",
            () -> RecordCodecBuilder.create(instance -> codecStart(instance)
                    .and(ForgeRegistries.ITEMS.getCodec().fieldOf("shield").forGetter(modifier -> modifier.shield))
                    .apply(instance, AddShieldLootModifier::new)));

    private final Item shield;

    public AddShieldLootModifier(LootItemCondition[] conditions, Item shield) {
        super(conditions);
        this.shield = shield;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext context) {
        loot.add(new ItemStack(shield));
        return loot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
