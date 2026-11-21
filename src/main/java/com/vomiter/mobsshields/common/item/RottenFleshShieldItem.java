package com.vomiter.mobsshields.common.item;

import com.vomiter.mobsshields.common.counterattack.reinforcement.RottenFleshReinforcements;
import com.vomiter.mobsshields.common.counterattack.ShieldCounterattack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class RottenFleshShieldItem extends AbstractMobShieldItem {
    public RottenFleshShieldItem(Properties properties) {
        this(properties, RottenFleshReinforcements::summon);
    }

    public RottenFleshShieldItem(Properties properties, ShieldCounterattack counterattack) {
        super(properties, counterattack);
    }

    @Override
    protected Item repairMaterial() { return Items.ROTTEN_FLESH; }
}
