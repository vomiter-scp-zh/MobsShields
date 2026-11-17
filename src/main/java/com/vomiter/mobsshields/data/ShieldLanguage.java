package com.vomiter.mobsshields.data;

import com.vomiter.mobsshields.MobsShields;
import com.vomiter.mobsshields.common.ShieldItems;
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
        add(ShieldItems.HOGLIN_SHIELD.get(), chinese ? "疣豬盾" : "Hoglin Shield");
        add(ShieldItems.GOLDEN_HOGLIN_SHIELD.get(), chinese ? "金色疣豬盾" : "Golden Hoglin Shield");
        add(ShieldItems.SQUID_SHIELD.get(), chinese ? "烏賊盾" : "Squid Shield");
        add("tooltip.mobsshields.squid_shield", chinese ? "格擋近戰攻擊時噴墨，使攻擊者陷入黑暗；在水中向後推進。"
                : "Blocking melee attacks releases ink and darkens the attacker; retreats in water.");
        add("tooltip.mobsshields.hoglin_shield", chinese ? "格擋近戰攻擊時伸出獠牙，推開攻擊者。" : "Blocking melee attacks extends the tusks and pushes the attacker away.");
        add("tooltip.mobsshields.golden_hoglin_shield", chinese ? "格擋近戰攻擊時伸出獠牙，傷害並強力擊退攻擊者。" : "Blocking melee attacks extends the tusks, damaging and strongly knocking back the attacker.");
        add("death.attack.mobsshields.hoglin_counterattack", chinese ? "%1$s 被 %2$s 的疣豬盾獠牙刺穿了" : "%1$s was gored by %2$s's hoglin shield");
        add("death.attack.mobsshields.hoglin_counterattack.item", chinese ? "%1$s 被 %2$s 使用 %3$s 的獠牙刺穿了" : "%1$s was gored by %2$s using %3$s");
    }
}
