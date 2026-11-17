package com.vomiter.mobsshields.client;

import com.vomiter.mobsshields.client.renderer.HoglinShieldRenderer;
import com.vomiter.mobsshields.client.renderer.SquidShieldRenderer;
import com.vomiter.mobsshields.common.AbstractMobShieldItem;
import com.vomiter.mobsshields.common.HoglinShieldItem;
import com.vomiter.mobsshields.common.SquidShieldItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public final class ShieldItemExtensions implements IClientItemExtensions {
    private final Supplier<? extends BlockEntityWithoutLevelRenderer> renderer;

    public ShieldItemExtensions(Supplier<? extends BlockEntityWithoutLevelRenderer> renderer) {
        this.renderer = renderer;
    }

    public static ShieldItemExtensions forShield(AbstractMobShieldItem item) {
        if (item instanceof SquidShieldItem) return new ShieldItemExtensions(SquidShieldRenderer::getInstance);
        if (item instanceof HoglinShieldItem) return new ShieldItemExtensions(HoglinShieldRenderer::getInstance);
        throw new IllegalArgumentException("No renderer registered for " + item.getClass().getSimpleName());
    }

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() { return renderer.get(); }
}
