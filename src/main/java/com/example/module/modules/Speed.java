package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.ModeSetting;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;

public class Speed extends Module {
    private final NumberSetting multiplier = new NumberSetting("Multiplier", 1.5, 1.0, 3.0, 0.1);
    private final ModeSetting mode = new ModeSetting("Mode", "Attribute", "Attribute", "Strafe");

    public Speed() {
        super("Speed", "Move faster", Category.MOVEMENT);
        addSetting(multiplier);
        addSetting(mode);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;

        if (mode.getMode().equals("Attribute")) {
            var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (attr != null) attr.setBaseValue(0.1 * multiplier.getValue());
        } else {
            if (player.input.movementForward > 0 && player.isOnGround()) {
                var v = player.getVelocity();
                double boost = 1.15 * (multiplier.getValue() / 1.5);
                player.setVelocity(v.x * boost, v.y, v.z * boost);
            }
        }
    }

    @Override
    public void onDisable() {
        var player = MinecraftClient.getInstance().player;
        if (player != null) {
            var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (attr != null) attr.setBaseValue(0.1);
        }
    }
}
