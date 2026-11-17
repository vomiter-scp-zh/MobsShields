package com.vomiter.mobsshields.common;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ShieldConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.DoubleValue HOGLIN_KNOCKBACK;
    public static final ForgeConfigSpec.DoubleValue GOLDEN_KNOCKBACK;
    public static final ForgeConfigSpec.DoubleValue GOLDEN_DAMAGE;
    public static final ForgeConfigSpec.IntValue COOLDOWN;
    public static final ForgeConfigSpec.DoubleValue MAX_DISTANCE;

    public static final ForgeConfigSpec.IntValue SQUID_DARKNESS_TICKS;
    public static final ForgeConfigSpec.DoubleValue SQUID_RETREAT_SPEED;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("counterattack");
        HOGLIN_KNOCKBACK = builder.comment("Extra knockback after blocking a direct melee hit.")
                .defineInRange("hoglinKnockback", 0.35, 0.0, 4.0);
        GOLDEN_KNOCKBACK = builder.defineInRange("goldenHoglinKnockback", 1.1, 0.0, 4.0);
        GOLDEN_DAMAGE = builder.comment("Damage points, before armor; 2 points = 1 heart.")
                .defineInRange("goldenHoglinDamage", 4.0, 0.0, 40.0);
        COOLDOWN = builder.defineInRange("cooldownTicks", 20, 12, 1200);
        MAX_DISTANCE = builder.comment("Maximum distance to the direct melee attacker. Projectiles never trigger a counterattack.")
                .defineInRange("maxDistance", 4.0, 0.5, 16.0);
        SQUID_DARKNESS_TICKS = builder.comment("Darkness duration after a squid shield blocks melee; 0 disables darkness.")
                .defineInRange("squidDarknessTicks", 60, 0, 1200);
        SQUID_RETREAT_SPEED = builder.comment("Horizontal retreat speed in water, in blocks per tick; 0 disables retreat.")
                .defineInRange("squidRetreatSpeed", 0.65, 0.0, 2.0);
        builder.pop();
        SPEC = builder.build();
    }

    private ShieldConfig() {}
}
