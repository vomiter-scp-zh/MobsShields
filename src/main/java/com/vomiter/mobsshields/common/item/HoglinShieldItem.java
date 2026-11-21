package com.vomiter.mobsshields.common.item;

import com.vomiter.mobsshields.common.counterattack.ShieldCounterattack;
import com.vomiter.mobsshields.common.counterattack.ShieldCounterattacks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class HoglinShieldItem extends AbstractMobShieldItem {
    private final boolean golden;

    public HoglinShieldItem(boolean golden, Properties properties) {
        this(golden, properties, ShieldCounterattacks.hoglin(golden));
    }

    public HoglinShieldItem(boolean golden, Properties properties, ShieldCounterattack counterattack) {
        super(properties, "tooltip.mobsshields." + (golden ? "golden_hoglin_shield" : "hoglin_shield"), counterattack);
        this.golden = golden;
    }

    public boolean isGolden() { return golden; }

    @Override
    protected Item repairMaterial() {
        return golden ? Items.GOLD_INGOT : Items.IRON_INGOT;
    }
}
