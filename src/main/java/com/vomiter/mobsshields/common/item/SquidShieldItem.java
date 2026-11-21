package com.vomiter.mobsshields.common.item;

import com.vomiter.mobsshields.common.counterattack.ShieldCounterattack;
import com.vomiter.mobsshields.common.counterattack.ShieldCounterattacks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class SquidShieldItem extends AbstractMobShieldItem {
    public SquidShieldItem(Properties properties) {
        this(properties, ShieldCounterattacks::squid);
    }

    public SquidShieldItem(Properties properties, ShieldCounterattack counterattack) {
        super(properties, "tooltip.mobsshields.squid_shield", counterattack);
    }

    @Override
    protected Item repairMaterial() { return Items.COPPER_INGOT; }
}
