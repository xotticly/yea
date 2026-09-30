package com.example.module.modules;

import com.example.module.Module;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import java.awt.Color;

public class TargetHud extends Module {
    private PlayerEntity target;
    private long lastAttackTime = 0;
    private boolean hooked = false;

    public TargetHud() {
        super("TargetHud", "Shows info about your target", Category.RENDER);
    }

    @Override
    public void onEnable() {
        if (hooked) return;
        hooked = true;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled()) return;
            if (client.targetedEntity instanceof PlayerEntity p) {
                target = p;
                lastAttackTime = System.currentTimeMillis();
            }
            if (target != null && System.currentTimeMillis() - lastAttackTime > 5000) {
                target = null;
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (!isEnabled() || target == null) return;
            var mc = MinecraftClient.getInstance();
            var tr = mc.textRenderer;

            int x = mc.getWindow().getScaledWidth() / 2 + 20;
            int y = mc.getWindow().getScaledHeight() / 2 - 20;

            drawContext.fill(x, y, x + 120, y + 45, new Color(0, 0, 0, 140).getRGB());
            drawContext.drawText(tr, target.getName().getString(), x + 5, y + 5, -1, true);

            float hp = target.getHealth();
            float maxHp = target.getMaxHealth();
            int barWidth = 110;
            int fill = (int) (barWidth * Math.max(0, hp / maxHp));
            drawContext.fill(x + 5, y + 20, x + 5 + barWidth, y + 28, new Color(60, 60, 60).getRGB());
            Color hpColor = hp > 12 ? new Color(80, 255, 80) : hp > 6 ? new Color(255, 200, 0) : new Color(255, 60, 60);
            drawContext.fill(x + 5, y + 20, x + 5 + fill, y + 28, hpColor.getRGB());

            drawContext.drawText(tr, String.format("%.1f HP", hp), x + 5, y + 32, -1, true);
            float dist = target.distanceTo(mc.player);
            drawContext.drawText(tr, String.format("%.1fm", dist), x + 90, y + 32, -1, true);
        });
    }
}
