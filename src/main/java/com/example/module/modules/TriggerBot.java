package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.BoolSetting;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.Hand;

public class TriggerBot extends Module {
    private final NumberSetting minCps = new NumberSetting("Min CPS", 8, 1, 20, 1);
    private final NumberSetting maxCps = new NumberSetting("Max CPS", 12, 1, 20, 1);
    private final BoolSetting playersOnly = new BoolSetting("Players Only", false);
    private long nextClick = 0;

    public TriggerBot() {
        super("TriggerBot", "Attack on crosshair target", Category.COMBAT);
        addSetting(minCps);
        addSetting(maxCps);
        addSetting(playersOnly);
    }

    @Override
    public void onTick() {
        var mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.currentScreen != null) return;
        if (mc.crosshairTarget == null || mc.crosshairTarget.getType() != HitResult.Type.ENTITY) return;
        Entity target = ((net.minecraft.util.hit.EntityHitResult) mc.crosshairTarget).getEntity();
        if (!(target instanceof LivingEntity)) return;
        if (playersOnly.getValue() && !(target instanceof PlayerEntity)) return;

        long now = System.currentTimeMillis();
        if (now < nextClick) return;
        if (mc.player.getAttackCooldownProgress(0.5f) < 1.0f) return;

        int lo = Math.min(minCps.getInt(), maxCps.getInt());
        int hi = Math.max(minCps.getInt(), maxCps.getInt());
        int cps = lo + (int) (Math.random() * (hi - lo + 1));
        nextClick = now + 1000 / cps;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
