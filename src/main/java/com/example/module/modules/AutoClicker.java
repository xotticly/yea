package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.ModeSetting;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;

public class AutoClicker extends Module {
    private final NumberSetting cps = new NumberSetting("CPS", 10, 1, 20, 1);
    private final ModeSetting mode = new ModeSetting("Mode", "Attack", "Attack", "Break");
    private long nextClick = 0;

    public AutoClicker() {
        super("AutoClicker", "Clicks for you", Category.MISC);
        addSetting(cps);
        addSetting(mode);
    }

    @Override
    public void onTick() {
        var mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.currentScreen != null) return;

        long now = System.currentTimeMillis();
        if (now < nextClick) return;

        int base = 1000 / cps.getInt();
        nextClick = now + base + (long) (Math.random() * base * 0.3);

        if (mode.getMode().equals("Attack") && mc.options.attackKey.isPressed()
                && mc.crosshairTarget != null
                && mc.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.ENTITY) {
            mc.doAttack();
        } else if (mode.getMode().equals("Break") && mc.options.useKey.isPressed()) {
            mc.doItemUse();
        }
    }
}
