package com.vomiter.mobsshields.common;

import com.vomiter.mobsshields.MobsShields;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Latest owner orders outlive chunk unloads, player logouts and server restarts. */
public final class ReinforcementOrders extends SavedData {
    private static final String DATA_ID = MobsShields.MOD_ID + "_reinforcement_orders";
    private final Map<UUID, Order> orders = new HashMap<>();

    private ReinforcementOrders() {}

    public static ReinforcementOrders get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(
                ReinforcementOrders::load, ReinforcementOrders::new, DATA_ID);
    }

    public static long now(MinecraftServer server) { return server.overworld().getGameTime(); }

    public void command(UUID owner, LivingEntity target, long expiresAt) {
        Order old = orders.get(owner);
        long until = old == null ? expiresAt : Math.max(expiresAt, old.expiresAt());
        orders.put(owner, new Order(target.getUUID(), target.level().dimension().location().toString(), until));
        setDirty();
    }

    public Order find(UUID owner, long now) {
        Order order = orders.get(owner);
        if (order != null && now >= order.expiresAt()) {
            orders.remove(owner);
            setDirty();
            return null;
        }
        return order;
    }

    public void prune(long now) {
        if (orders.values().removeIf(order -> now >= order.expiresAt())) setDirty();
    }

    public static ReinforcementOrders load(CompoundTag tag) {
        ReinforcementOrders data = new ReinforcementOrders();
        ListTag list = tag.getList("orders", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            if (entry.hasUUID("owner") && entry.hasUUID("target")) {
                data.orders.put(entry.getUUID("owner"), new Order(entry.getUUID("target"),
                        entry.getString("dimension"), entry.getLong("expiresAt")));
            }
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        orders.forEach((owner, order) -> {
            CompoundTag entry = new CompoundTag();
            entry.putUUID("owner", owner);
            entry.putUUID("target", order.target());
            entry.putString("dimension", order.dimension());
            entry.putLong("expiresAt", order.expiresAt());
            list.add(entry);
        });
        tag.put("orders", list);
        return tag;
    }

    public record Order(UUID target, String dimension, long expiresAt) {}
}
