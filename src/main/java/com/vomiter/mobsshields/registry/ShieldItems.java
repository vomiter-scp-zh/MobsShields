package com.vomiter.mobsshields.registry;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.common.item.WitherRibShieldItem;
import com.vomiter.mobsshields.common.item.HoglinShieldItem;
import com.vomiter.mobsshields.common.item.RottenFleshShieldItem;
import com.vomiter.mobsshields.common.item.SquidShieldItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ShieldItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MobsShields.MOD_ID);
    public static final RegistryObject<HoglinShieldItem> HOGLIN_SHIELD = ITEMS.register("hoglin_shield",
            () -> new HoglinShieldItem(false, new Item.Properties().durability(384)));
    public static final RegistryObject<HoglinShieldItem> GOLDEN_HOGLIN_SHIELD = ITEMS.register("golden_hoglin_shield",
            () -> new HoglinShieldItem(true, new Item.Properties().durability(576)));

    public static final RegistryObject<SquidShieldItem> SQUID_SHIELD = ITEMS.register("squid_shield",
            () -> new SquidShieldItem(new Item.Properties().durability(384)));

    public static final RegistryObject<RottenFleshShieldItem> ROTTEN_FLESH_SHIELD = ITEMS.register("rotten_flesh_shield",
            () -> new RottenFleshShieldItem(new Item.Properties().durability(336)));

    public static final RegistryObject<WitherRibShieldItem> WITHER_RIB_SHIELD = ITEMS.register("wither_rib_shield",
            () -> new WitherRibShieldItem(new Item.Properties().durability(384)));

    private ShieldItems() {}
}
