package com.example.module.modules;

import com.example.module.Module;
import com.example.render.RenderUtils;
import com.example.settings.ModeSetting;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Box;
import java.awt.Color;

public class ChestESP extends Module {
    private final ModeSetting mode = new ModeSetting("Highlight", "Chests", "Chests", "All Storage");
    private boolean hooked = false;

    public ChestESP() {
        super("ChestESP", "See storage blocks through walls", Category.RENDER);
        addSetting(mode);
    }

    @Override
    public void onEnable() {
        if (hooked) return;
        hooked = true;
        WorldRenderEvents.LAST.register(context -> {
            if (!isEnabled()) return;
            var mc = MinecraftClient.getInstance();
            if (mc.world == null) return;

            var vcp = RenderUtils.beginLines();
            for (BlockEntity be : mc.world.blockEntities()) {
                boolean isStorage = be instanceof ChestBlockEntity
                        || be instanceof BarrelBlockEntity
                        || be instanceof ShulkerBoxBlockEntity;
                if (!isStorage) continue;
                if (mode.getMode().equals("Chests") && !(be instanceof ChestBlockEntity)) continue;

                Box box = new Box(be.getPos());
                Color c = be instanceof ChestBlockEntity
                        ? new Color(255, 200, 0, 160)
                        : new Color(180, 100, 255, 160);
                RenderUtils.drawBox(context.matrixStack(), vcp, box, c, 1.5f);
            }
            vcp.draw();
        });
    }
}
