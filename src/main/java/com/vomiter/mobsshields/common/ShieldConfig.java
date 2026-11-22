package com.vomiter.mobsshields.common;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ShieldConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.DoubleValue HOGLIN_KNOCKBACK;
    public static final ForgeConfigSpec.DoubleValue GOLDEN_KNOCKBACK;
    public static final ForgeConfigSpec.DoubleValue GOLDEN_DAMAGE;

    public static final ForgeConfigSpec.IntValue SQUID_DARKNESS_TICKS;
    public static final ForgeConfigSpec.DoubleValue SQUID_RETREAT_SPEED;

    public static final ForgeConfigSpec.DoubleValue ROTTEN_SUMMON_CHANCE;
    public static final ForgeConfigSpec.IntValue ROTTEN_LIFETIME_TICKS;
    public static final ForgeConfigSpec.IntValue ROTTEN_MAX_NEARBY;

    public static final ForgeConfigSpec.IntValue WITHER_WEAPON_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue WITHER_ARMED_FATIGUE_CHANCE;
    public static final ForgeConfigSpec.DoubleValue WITHER_UNARMED_WEAKNESS_CHANCE;
    public static final ForgeConfigSpec.DoubleValue WITHER_UNARMED_FATIGUE_CHANCE;
    public static final ForgeConfigSpec.IntValue WITHER_EFFECT_TICKS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("counterattack");
        HOGLIN_KNOCKBACK = builder.comment("Extra knockback after using a hoglin shield to block a direct melee hit.")
                .defineInRange("hoglinKnockback", 0.35, 0.0, 4.0);
        GOLDEN_KNOCKBACK = builder.defineInRange("goldenHoglinKnockback", 1.1, 0.0, 4.0);
        GOLDEN_DAMAGE = builder.comment("Damage caused by golden hoglin shield counterattack")
                .defineInRange("goldenHoglinDamage", 3.0, 0.0, 40.0);
        SQUID_DARKNESS_TICKS = builder.comment("Darkness duration after a squid shield blocks melee; 0 disables darkness.")
                .defineInRange("squidDarknessTicks", 60, 0, 1200);
        SQUID_RETREAT_SPEED = builder.comment("Horizontal retreat speed in water, in blocks per tick; 0 disables retreat.")
                .defineInRange("squidRetreatSpeed", 0.65, 0.0, 2.0);
        builder.pop();
        builder.push("rottenFleshShield");
        ROTTEN_SUMMON_CHANCE = builder.comment("Chance per eligible melee counterattack to summon one zombie.")
                .defineInRange("summonChance", 0.25, 0.0, 1.0);
        ROTTEN_LIFETIME_TICKS = builder.comment("Time span from a reinforcement zombie spawning to its despawning.")
                .defineInRange("lifetimeTicks", 600, 20, 12000);
        ROTTEN_MAX_NEARBY = builder.comment("Maximum living summons belonging to a shield holder within 32 blocks.")
                .defineInRange("maxNearbySummons", 3, 1, 16);
        builder.pop();
        builder.push("witherRibShield");
        WITHER_WEAPON_DAMAGE = builder.comment("Extra durability damage to the attacking main-hand item; respects Unbreaking and creative mode.")
                .defineInRange("weaponDurabilityDamage", 3, 0, 1000);
        WITHER_ARMED_FATIGUE_CHANCE = builder.comment("Chance of Mining Fatigue I when blocking a sword, trident or DiggerItem tool attack.")
                .defineInRange("armedFatigueChance", 0.10, 0.0, 1.0);
        WITHER_UNARMED_WEAKNESS_CHANCE = builder.comment("Independent chance of Weakness I when blocking an empty-main-hand attack.")
                .defineInRange("unarmedWeaknessChance", 0.50, 0.0, 1.0);
        WITHER_UNARMED_FATIGUE_CHANCE = builder.comment("Independent chance of Mining Fatigue I when blocking an empty-main-hand attack.")
                .defineInRange("unarmedFatigueChance", 0.25, 0.0, 1.0);
        WITHER_EFFECT_TICKS = builder.comment("Weakness and Mining Fatigue duration, in ticks; 0 disables effects.")
                .defineInRange("effectDurationTicks", 100, 0, 12000);
        builder.pop();
        SPEC = builder.build();
    }

    private ShieldConfig() {}
}
