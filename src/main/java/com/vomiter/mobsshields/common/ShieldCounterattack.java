package com.vomiter.mobsshields.common;

/** Called once on the server after a successful, eligible melee block. */
@FunctionalInterface
public interface ShieldCounterattack {
    void execute(CounterattackContext context);
}
