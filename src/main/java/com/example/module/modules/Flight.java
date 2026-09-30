package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;

public class Flight extends Module {
    private final NumberSetting speed = new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.1);

    public Flight() {
        super("Flight", "Creative-style fly", Category.MOVEMENT);
        addSetting(speed);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.getAbilities().flying = true;
        player.getAbilities().flySpeed = speed.getFloat() * 0.05f;
    }

    @Override
    public void onDisable() {
        var player = MinecraftClient.getInstance().player;
        if (player != null && !player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
        }
    }
}
