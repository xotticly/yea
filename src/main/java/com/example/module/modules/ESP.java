package com.example.module.modules;

import com.example.module.Module;
import com.example.render.RenderUtils;
import com.example.settings.BoolSetting;
import com.example.settings.ModeSetting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import java.awt.Color;

public class ESP extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Box", "Box", "Glow");
    private final BoolSetting showFriends = new BoolSetting("Team Colors", true);
    private boolean hooked = false;

    public ESP() {
        super("ESP", "See players through walls", Category.RENDER);
        addSetting(mode);
        addSetting(showFriends);
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

                Color c = showFriends.getValue()
                        ? RenderUtils.getPlayerColor(entity, new Color(255, 60, 60), new Color(60, 255, 60))
                        : new Color(255, 60, 60);

                if (mode.getMode().equals("Box")) {
                    RenderUtils.drawBox(context.matrixStack(), vcp,
                            entity.getBoundingBox(), c, 2.0f);
                }
                if (mode.getMode().equals("Glow")) {
                    entity.setGlowing(true);
                } else if (entity.isGlowing()) {
                    entity.setGlowing(false);
                }
            }
            vcp.draw();
        });
    }

    @Override
    public void onDisable() {
        var mc = MinecraftClient.getInstance();
        if (mc.world != null) {
            for (var entity : mc.world.getPlayers()) {
                if (entity.isGlowing()) entity.setGlowing(false);
            }
        }
    }
}
