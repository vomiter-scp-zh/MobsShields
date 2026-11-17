package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.common.ShieldItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.data.tags.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public final class ShieldItemTags extends ItemTagsProvider {
    public ShieldItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper helper) {
        super(output, lookup, CompletableFuture.completedFuture(TagLookup.empty()), MobsShields.MOD_ID, helper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.TOOLS_SHIELDS).add(ShieldItems.WITHER_RIB_SHIELD.get());
        tag(Tags.Items.TOOLS_SHIELDS).add(ShieldItems.HOGLIN_SHIELD.get(), ShieldItems.GOLDEN_HOGLIN_SHIELD.get(), ShieldItems.SQUID_SHIELD.get(), ShieldItems.ROTTEN_FLESH_SHIELD.get());
    }
}
