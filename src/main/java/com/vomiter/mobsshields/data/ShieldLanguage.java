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
        add(ShieldItems.WITHER_RIB_SHIELD.get(), chinese ? "凋零肋骨盾" : "Wither Rib Shield");
        add("tooltip.mobsshields.wither_rib_shield", chinese ? "格擋持物近戰攻擊時額外消耗攻擊物品耐久，並有機率施加挖掘疲勞；格擋空手攻擊時，分別判定虛弱與挖掘疲勞。"
                : "Blocking armed melee attacks wears down the attacking item and may inflict Mining Fatigue; barehanded attacks independently roll Weakness and Mining Fatigue.");
        add(ShieldItems.HOGLIN_SHIELD.get(), chinese ? "疣豬盾" : "Hoglin Shield");
        add(ShieldItems.GOLDEN_HOGLIN_SHIELD.get(), chinese ? "金色疣豬盾" : "Golden Hoglin Shield");
        add(ShieldItems.ROTTEN_FLESH_SHIELD.get(), chinese ? "腐肉盾" : "Rotten Flesh Shield");
        add("effect.mobsshields.zombie_truce", chinese ? "亡者號令" : "Undead Command");
        add("tooltip.mobsshields.rotten_flesh_shield", chinese ? "格擋近戰攻擊時有機率召喚殭屍；成功召喚後，在效果期間受傷或以腐肉盾格擋會指揮所有增援攻擊新攻擊者。"
                : "Blocking melee attacks may summon a zombie; after a successful summon, taking damage or blocking with this shield during the effect commands all reinforcements to attack the new attacker.");
        add(ShieldItems.SQUID_SHIELD.get(), chinese ? "烏賊盾" : "Squid Shield");
        add("tooltip.mobsshields.squid_shield", chinese ? "格擋近戰攻擊時噴墨，使攻擊者陷入黑暗；在水中向後推進。"
                : "Blocking melee attacks releases ink and darkens the attacker; retreats in water.");
        add("tooltip.mobsshields.hoglin_shield", chinese ? "格擋近戰攻擊時伸出獠牙，推開攻擊者。" : "Blocking melee attacks extends the tusks and pushes the attacker away.");
        add("tooltip.mobsshields.golden_hoglin_shield", chinese ? "格擋近戰攻擊時伸出獠牙，傷害並強力擊退攻擊者。" : "Blocking melee attacks extends the tusks, damaging and strongly knocking back the attacker.");
        add("death.attack.mobsshields.hoglin_counterattack", chinese ? "%1$s 被 %2$s 的疣豬盾獠牙刺穿了" : "%1$s was gored by %2$s's hoglin shield");
        add("death.attack.mobsshields.hoglin_counterattack.item", chinese ? "%1$s 被 %2$s 使用 %3$s 的獠牙刺穿了" : "%1$s was gored by %2$s using %3$s");
    }
}
