package com.vomiter.mobsshields.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class RottenFleshShieldItem extends AbstractMobShieldItem {
    public RottenFleshShieldItem(Properties properties) {
        this(properties, RottenFleshReinforcements::summon);
    }

    public RottenFleshShieldItem(Properties properties, ShieldCounterattack counterattack) {
        super(properties, "tooltip.mobsshields.rotten_flesh_shield", counterattack);
    }

    @Override
    protected Item repairMaterial() { return Items.ROTTEN_FLESH; }
}
