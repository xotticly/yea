package com.example.module.modules;

import com.example.module.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Criticals extends Module {
    private boolean hooked = false;

    public Criticals() {
        super("Criticals", "Critical hits without jumping", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        if (hooked) return;
        hooked = true;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled()) return;
            var player = client.player;
            if (player == null || client.crosshairTarget == null) return;
            if (client.options.attackKey.isPressed() && player.isOnGround()
                    && client.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.ENTITY) {
                var net = player.networkHandler;
                net.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        player.getX(), player.getY() + 0.0625, player.getZ(), false));
                net.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        player.getX(), player.getY(), player.getZ(), false));
            }
        });
    }
}
