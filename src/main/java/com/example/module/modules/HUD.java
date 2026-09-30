package com.example.module.modules;

import com.example.Client;
import com.example.module.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import java.awt.Color;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Watermark + enabled modules", Category.RENDER);
        setEnabled(true);
    }

    private boolean registered = false;

    @Override
    public void onEnable() {
        if (registered) return;
        registered = true;
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (!isEnabled()) return;
            TextRenderer tr = MinecraftClient.getInstance().textRenderer;
            drawContext.drawText(tr, Client.NAME + " v" + Client.VERSION, 4, 4, new Color(140, 0, 255).getRGB(), true);
            var mods = Client.moduleManager.getEnabled();
            mods.sort((a, b) -> Integer.compare(
                    tr.getWidth(b.getName()), tr.getWidth(a.getName())));
            int y = 16;
            for (Module m : mods) {
                drawContext.drawText(tr, m.getName(), 4, y, -1, true);
                y += 10;
            }
        });
    }
}
