package com.vomiter.mobsshields.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.vomiter.mobsshields.common.ShieldConfig;
import com.vomiter.mobsshields.common.item.AbstractMobShieldItem;
import com.vomiter.mobsshields.common.item.HoglinShieldItem;
import com.vomiter.mobsshields.common.item.RottenFleshShieldItem;
import com.vomiter.mobsshields.common.item.SquidShieldItem;
import com.vomiter.mobsshields.common.item.WitherRibShieldItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.glfw.GLFW;

import java.math.BigDecimal;
import java.util.List;

public final class ShieldTooltips {
    private ShieldTooltips() {}

    public static void onTooltip(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof AbstractMobShieldItem shield)) return;
        List<Component> lines = event.getToolTip();
        String lootSource = shield instanceof HoglinShieldItem hoglin
                ? (hoglin.isGolden() ? "golden_hoglin_source" : "bastion")
                : shield instanceof SquidShieldItem ? "ocean" : null;
        if (lootSource != null) {
            if (Screen.hasShiftDown()) {
                lines.add(text(lootSource));
            } else {
                lines.add(text("loot_hint").withStyle(ChatFormatting.GRAY));
            }
        }

        long window = Minecraft.getInstance().getWindow().getWindow();
        boolean controlDown = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL)
                || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_CONTROL);
        if (!controlDown) {
            lines.add(text("counter_hint").withStyle(ChatFormatting.GRAY));
            return;
        }
        if (!ShieldConfig.SPEC.isLoaded()) return;
        int header = lines.size();
        lines.add(text("on_block").withStyle(ChatFormatting.GRAY));
        if (shield instanceof HoglinShieldItem hoglin) {
            if (hoglin.isGolden() && ShieldConfig.GOLDEN_DAMAGE.get() > 0) {
                lines.add(text("damage", number(ShieldConfig.GOLDEN_DAMAGE.get())).withStyle(ChatFormatting.GOLD));
            }
            if ((hoglin.isGolden() ? ShieldConfig.GOLDEN_KNOCKBACK : ShieldConfig.HOGLIN_KNOCKBACK).get() > 0) {
                boolean strong = hoglin.isGolden() && ShieldConfig.GOLDEN_KNOCKBACK.get() > ShieldConfig.HOGLIN_KNOCKBACK.get();
                lines.add(text(strong ? "strong_knockback" : "knockback").withStyle(ChatFormatting.GOLD));
            }
        } else if (shield instanceof SquidShieldItem) {
            if (ShieldConfig.SQUID_DARKNESS_TICKS.get() > 0) {
                lines.add(text("ink", number(ShieldConfig.SQUID_DARKNESS_TICKS.get() / 20.0)).withStyle(ChatFormatting.AQUA));
            }
            if (ShieldConfig.SQUID_RETREAT_SPEED.get() > 0) lines.add(text("retreat").withStyle(ChatFormatting.AQUA));
        } else if (shield instanceof RottenFleshShieldItem && ShieldConfig.ROTTEN_SUMMON_CHANCE.get() > 0) {
            lines.add(text("summon", percent(ShieldConfig.ROTTEN_SUMMON_CHANCE.get())).withStyle(ChatFormatting.GREEN));
            lines.add(text("command"));
        } else if (shield instanceof WitherRibShieldItem) {
            witherDetails(lines);
        }
        if (lines.size() == header + 1) lines.add(text("disabled").withStyle(ChatFormatting.GRAY));
    }

    private static void witherDetails(List<Component> lines) {
        int wear = ShieldConfig.WITHER_WEAPON_DAMAGE.get();
        if (wear > 0) lines.add(text("wear", wear));
        if (ShieldConfig.WITHER_EFFECT_TICKS.get() <= 0) return;
        double armed = ShieldConfig.WITHER_ARMED_FATIGUE_CHANCE.get();
        double weakness = ShieldConfig.WITHER_UNARMED_WEAKNESS_CHANCE.get();
        double fatigue = ShieldConfig.WITHER_UNARMED_FATIGUE_CHANCE.get();
        if (armed == 0 && weakness == 0 && fatigue == 0) return;
        lines.add(text("can_inflict").withStyle(ChatFormatting.GRAY));
        effect(lines, MobEffects.DIG_SLOWDOWN, armed, "armed");
        effect(lines, MobEffects.WEAKNESS, weakness, "bare");
        effect(lines, MobEffects.DIG_SLOWDOWN, fatigue, "bare");
    }

    private static void effect(List<Component> lines, MobEffect effect, double chance, String condition) {
        if (chance == 0) return;
        Component name = Component.translatable(effect.getDescriptionId()).append(" I").withStyle(ChatFormatting.RED);
        lines.add(text(condition, name, percent(chance)).withStyle(ChatFormatting.GRAY));
    }

    private static String number(double value) {
        return BigDecimal.valueOf(value).stripTrailingZeros().toPlainString();
    }

    private static String percent(double value) {
        return BigDecimal.valueOf(value).movePointRight(2).stripTrailingZeros().toPlainString() + "%";
    }

    private static MutableComponent text(String key, Object... args) {
        return Component.translatable("tooltip.mobsshields." + key, args);
    }
}
