package com.vomiter.mobsshields.common.counterattack;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public record CounterattackContext(ServerLevel level, LivingEntity holder,
                                  LivingEntity attacker, ItemStack shield, float blockedDamage,
                                  ItemStack attackWeapon, boolean barehanded, boolean weaponAttack) {
    public CounterattackContext(ServerLevel level, LivingEntity holder, LivingEntity attacker,
                                ItemStack shield, float blockedDamage, ItemStack attackWeapon, boolean barehanded) {
        this(level, holder, attacker, shield, blockedDamage, attackWeapon, barehanded, isWeapon(attackWeapon));
    }

    public static boolean isWeapon(ItemStack stack) {
        return stack.getAttributeModifiers(EquipmentSlot.MAINHAND).containsKey(Attributes.ATTACK_DAMAGE);
    }

    public CounterattackContext(ServerLevel level, LivingEntity holder, LivingEntity attacker,
                                ItemStack shield, float blockedDamage) {
        this(level, holder, attacker, shield, blockedDamage,
                attacker.getMainHandItem(), attacker.getMainHandItem().isEmpty());
    }
}
