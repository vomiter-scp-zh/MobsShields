package com.vomiter.mobsshields.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record CounterattackContext(ServerLevel level, LivingEntity holder,
                                  LivingEntity attacker, ItemStack shield, float blockedDamage) {}
