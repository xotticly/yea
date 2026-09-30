package com.example.module.modules;

import com.example.module.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class NoMissDelay extends Module {
    private boolean hooked = false;

    public NoMissDelay() {
        super("NoMissDelay", "No cooldown on missed attacks", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        if (hooked) return;
        hooked = true;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled()) return;
            var player = client.player;
            if (player != null && client.options.attackKey.isPressed()
                    && player.getAttackCooldownProgress(0.5f) < 1.0f) {
                player.resetLastAttackedTicks();
            }
        });
    }
}
