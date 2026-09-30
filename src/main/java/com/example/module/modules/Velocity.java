package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.NumberSetting;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Vec3d;

public class Velocity extends Module {
    private final NumberSetting horizontal = new NumberSetting("Horizontal", 20, 0, 100, 5);
    private final NumberSetting vertical = new NumberSetting("Vertical", 100, 0, 100, 5);
    private boolean hooked = false;

    public Velocity() {
        super("Velocity", "Reduce knockback", Category.COMBAT);
        addSetting(horizontal);
        addSetting(vertical);
    }

    @Override
    public void onEnable() {
        if (hooked) return;
        hooked = true;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled()) return;
            var player = client.player;
            if (player == null || player.hurtTime <= 0) return;
            Vec3d v = player.getVelocity();
            double h = horizontal.getValue() / 100.0;
            double y = v.y * (vertical.getValue() / 100.0);
            player.setVelocity(v.x * h, y, v.z * h);
        });
    }
}
