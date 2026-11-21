package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.registry.ShieldItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public final class ShieldLanguage extends LanguageProvider {
    private final boolean chinese;
    public ShieldLanguage(PackOutput output, String locale) {
        super(output, MobsShields.MOD_ID, locale);
        chinese = locale.equals("zh_tw");
    }

    @Override
    protected void addTranslations() {
        add(ShieldItems.WITHER_RIB_SHIELD.get(), chinese ? "凋零肋骨盾" : "Wither Rib Shield");
        add(ShieldItems.HOGLIN_SHIELD.get(), chinese ? "疣豬盾" : "Hoglin Shield");
        add(ShieldItems.GOLDEN_HOGLIN_SHIELD.get(), chinese ? "黃金疣豬盾" : "Golden Hoglin Shield");
        add(ShieldItems.ROTTEN_FLESH_SHIELD.get(), chinese ? "腐肉盾" : "Rotten Flesh Shield");
        add("effect.mobsshields.zombie_truce", chinese ? "亡者號令" : "Undead Command");
        add(ShieldItems.SQUID_SHIELD.get(), chinese ? "烏賊盾" : "Squid Shield");
        add("tooltip.mobsshields.loot_hint", chinese ? "按住 [Shift] 查看獲取途徑。" : "Hold [Shift] to see loot source.");
        add("tooltip.mobsshields.counter_hint", chinese ? "按住 [Ctrl] 查看反擊效果。" : "Hold [Ctrl] to see counterattack effects.");
        add("tooltip.mobsshields.bastion", chinese ? "  堡壘遺跡的疣豬獸欄箱與一般箱" : "  Bastion remnants: hoglin stable and generic chests");
        add("tooltip.mobsshields.ocean", chinese ? "  沉船寶箱、大型海底廢墟寶箱" : "  Shipwreck treasure chests and large ocean ruin chests");
        add("tooltip.mobsshields.on_block", chinese ? "格擋近戰攻擊時" : "When blocking a melee attack");
        add("tooltip.mobsshields.damage", chinese ? "  對攻擊者造成 %s 點傷害" : "  Deals %s damage to the attacker");
        add("tooltip.mobsshields.knockback", chinese ? "  擊退攻擊者" : "  Knocks the attacker back");
        add("tooltip.mobsshields.ink", chinese ? "  噴墨，使攻擊者陷入黑暗 %s 秒" : "  Sprays ink, inflicting Darkness for %s s");
        add("tooltip.mobsshields.retreat", chinese ? "  若身在水中，向後游開" : "  Propels you backward in water");
        add("tooltip.mobsshields.summon", chinese ? "  %s 機率召喚殭屍並獲得亡者號令" : "  %s chance to summon a zombie and gain Undead Command");
        add("tooltip.mobsshields.command", chinese ? "  號令期間，受傷或以此盾格擋會讓援軍轉攻該敵人" : "  While commanding, taking damage or blocking with this shield redirects your zombies to the attacker");
        add("tooltip.mobsshields.wear", chinese ? "  攻擊者的武器額外消耗 %s 點耐久" : "  Deals %s extra durability damage to the attacking weapon");
        add("tooltip.mobsshields.can_inflict", chinese ? "可能施加的效果" : "Can inflict:");
        add("tooltip.mobsshields.armed", chinese ? "  %1$s · %2$s（持近戰武器攻擊時）" : "  %1$s · %2$s (if attacked with a melee weapon)");
        add("tooltip.mobsshields.bare", chinese ? "  %1$s · %2$s（空手攻擊時）" : "  %1$s · %2$s (if attacked with a bare hand)");
        add("tooltip.mobsshields.strong_knockback", chinese ? "  強力擊退攻擊者" : "  Strongly knocks the attacker back");
        add("tooltip.mobsshields.disabled", chinese ? "  目前設定已停用此反擊效果" : "  Disabled by the current settings");
        add("death.attack.mobsshields.hoglin_counterattack", chinese ? "%1$s 被 %2$s 的疣豬盾獠牙刺穿了" : "%1$s was gored by %2$s's hoglin shield");
        add("death.attack.mobsshields.hoglin_counterattack.item", chinese ? "%1$s 被 %2$s 使用 %3$s 的獠牙刺穿了" : "%1$s was gored by %2$s using %3$s");
    }
}
