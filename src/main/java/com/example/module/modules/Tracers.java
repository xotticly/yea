package com.example.module.modules;

import com.example.module.Module;
import com.example.render.RenderUtils;
import com.example.settings.BoolSetting;
import com.example.settings.NumberSetting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;

public class Tracers extends Module {
    private final NumberSetting width = new NumberSetting("Line Width", 1.5, 0.5, 4.0, 0.5);
    private final BoolSetting toFeet = new BoolSetting("Target Feet", false);
    private boolean hooked = false;

    public Tracers() {
        super("Tracers", "Lines to players", Category.RENDER);
        addSetting(width);
        addSetting(toFeet);
    }

    @Override
    public void onEnable() {
        if (hooked) return;
        hooked = true;
        WorldRenderEvents.LAST.register(context -> {
            if (!isEnabled()) return;
            var mc = MinecraftClient.getInstance();
            if (mc.world == null || mc.player == null) return;

            var vcp = RenderUtils.beginLines();
            for (var entity : mc.world.getPlayers()) {
                if (entity == mc.player) continue;
                Vec3d target = toFeet.getValue()
                        ? entity.getPos()
                        : entity.getPos().add(0, entity.getHeight() / 2, 0);
                RenderUtils.drawTracer(context.matrixStack(), vcp, target, new Color(255, 255, 255, 180));
            }
            vcp.draw();
        });
    }
}
