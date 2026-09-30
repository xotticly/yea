package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "No fall damage", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        var network = MinecraftClient.getInstance().getNetworkHandler();
        if (player != null && network != null && player.fallDistance > 2.0f) {
            network.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }
}
