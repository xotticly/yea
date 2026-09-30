package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super("NoJumpDelay", "Jump instantly after landing", Category.MISC);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player != null) player.jumpTicks = 0;
    }
}
