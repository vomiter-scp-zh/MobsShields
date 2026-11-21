package com.vomiter.mobsshields.common.item;

import com.vomiter.mobsshields.common.counterattack.ShieldCounterattack;
import com.vomiter.mobsshields.common.counterattack.ShieldCounterattacks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class WitherRibShieldItem extends AbstractMobShieldItem {
    public WitherRibShieldItem(Properties properties) {
        this(properties, ShieldCounterattacks::witherRib);
    }

    public WitherRibShieldItem(Properties properties, ShieldCounterattack counterattack) {
        super(properties, "tooltip.mobsshields.wither_rib_shield", counterattack);
    }

    @Override
    protected Item repairMaterial() { return Items.BONE; }
}
