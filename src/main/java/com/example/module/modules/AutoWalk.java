package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("AutoWalk", "Walks forward automatically", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player != null) player.input.movementForward = 1.0f;
    }
}
