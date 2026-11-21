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
        super(properties, counterattack);
    }

    @Override
    protected Item repairMaterial() { return Items.BONE; }
}
