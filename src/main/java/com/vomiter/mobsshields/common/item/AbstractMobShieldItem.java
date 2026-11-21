package com.vomiter.mobsshields.common.item;

import com.vomiter.mobsshields.client.ShieldItemExtensions;
import com.vomiter.mobsshields.common.counterattack.CounterattackContext;
import com.vomiter.mobsshields.common.counterattack.CounterattackHandler;
import com.vomiter.mobsshields.common.counterattack.ShieldCounterattack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractMobShieldItem extends ShieldItem {
    private final ShieldCounterattack counterattack;

    protected AbstractMobShieldItem(Properties properties, ShieldCounterattack counterattack) {
        super(properties);
        this.counterattack = Objects.requireNonNull(counterattack);
    }

    public final void counterattack(CounterattackContext context) {
        counterattack.execute(context);
    }

    protected abstract Item repairMaterial();

    @Override
    public final boolean isValidRepairItem(ItemStack shield, ItemStack material) {
        return material.is(repairMaterial());
    }

    @Override
    public final void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ShieldItemExtensions.forShield(this));
    }

    @Override
    public final boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (slotChanged || !ItemStack.isSameItem(oldStack, newStack)) return true;
        ItemStack oldCopy = oldStack.copy();
        ItemStack newCopy = newStack.copy();
        oldCopy.removeTagKey(CounterattackHandler.ANIMATION_TAG);
        newCopy.removeTagKey(CounterattackHandler.ANIMATION_TAG);
        oldCopy.setDamageValue(0);
        newCopy.setDamageValue(0);
        return !ItemStack.isSameItemSameTags(oldCopy, newCopy);
    }
}
