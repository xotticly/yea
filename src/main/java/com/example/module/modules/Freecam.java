package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;

public class Freecam extends Module {
    private final NumberSetting speed = new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.1);
    private Perspective oldPerspective;
    private float oldYaw, oldPitch;

    public Freecam() {
        super("Freecam", "Detach camera from body", Category.MISC);
        addSetting(speed);
    }

    @Override
    public void onEnable() {
        var mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        oldPerspective = mc.options.getPerspective();
        oldYaw = mc.player.getYaw();
        oldPitch = mc.player.getPitch();
        mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;
        player.getAbilities().flying = true;
        player.getAbilities().flySpeed = speed.getFloat() * 0.05f;
        player.setNoGravity(true);
    }

    @Override
    public void onDisable() {
        var mc = MinecraftClient.getInstance();
        if (mc.player == null) return;
        mc.options.setPerspective(oldPerspective);
        mc.player.getAbilities().flying = false;
        mc.player.setNoGravity(false);
        mc.player.setYaw(oldYaw);
        mc.player.setPitch(oldPitch);
    }
}
