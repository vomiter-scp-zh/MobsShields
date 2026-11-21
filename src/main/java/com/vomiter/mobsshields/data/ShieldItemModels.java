package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.registry.ShieldItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public final class ShieldItemModels extends ItemModelProvider {
    public ShieldItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, MobsShields.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        shield(ShieldItems.HOGLIN_SHIELD.get());
        shield(ShieldItems.GOLDEN_HOGLIN_SHIELD.get());
        shield(ShieldItems.SQUID_SHIELD.get());
        shield(ShieldItems.ROTTEN_FLESH_SHIELD.get());
        shield(ShieldItems.WITHER_RIB_SHIELD.get());
    }

    private void shield(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        var blocking = withExistingParent(name + "_blocking", mcLoc("item/shield_blocking"))
                .texture("particle", modLoc("entity/shield/" + name));
        withExistingParent(name, mcLoc("item/shield"))
                .texture("particle", modLoc("entity/shield/" + name))
                .override().predicate(MobsShields.id("blocking"), 1).model(blocking).end();
    }
}
