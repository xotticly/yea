package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", "Respawn instantly on death", Category.MISC);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player != null && player.isDead()) {
            player.requestRespawn();
        }
    }
}
