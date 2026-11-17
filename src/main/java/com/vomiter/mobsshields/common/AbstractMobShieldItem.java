package com.vomiter.mobsshields.common;

import com.vomiter.mobsshields.client.ShieldItemExtensions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractMobShieldItem extends ShieldItem {
    private final ShieldCounterattack counterattack;
    private final String tooltipKey;

    protected AbstractMobShieldItem(Properties properties, String tooltipKey, ShieldCounterattack counterattack) {
        super(properties);
        this.tooltipKey = Objects.requireNonNull(tooltipKey);
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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
    }
}
