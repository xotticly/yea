package com.example.ui;

import com.example.Client;
import com.example.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;
import java.awt.Color;

/** Minimal placeholder GUI - lists modules by category, click to toggle.
 *  Replace with a proper draggable-panel GUI later. */
public class ClickGui extends Screen {
    public ClickGui() {
        super(Text.literal("MyClient"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int x = 10;
        for (Module.Category cat : Module.Category.values()) {
            context.drawText(textRenderer, "§l" + cat.name(), x, 10, new Color(140, 0, 255).getRGB(), true);
            List<Module> mods = Client.moduleManager.getByCategory(cat);
            int y = 22;
            for (Module m : mods) {
                int color = m.isEnabled() ? new Color(80, 255, 80).getRGB() : new Color(200, 200, 200).getRGB();
                context.drawText(textRenderer, m.getName(), x, y, color, true);
                y += 12;
            }
            x += 110;
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = 10;
        for (Module.Category cat : Module.Category.values()) {
            List<Module> mods = Client.moduleManager.getByCategory(cat);
            int y = 22;
            for (Module m : mods) {
                if (mouseX >= x && mouseX <= x + 100 && mouseY >= y && mouseY <= y + 11) {
                    m.toggle();
                    return true;
                }
                y += 12;
            }
            x += 110;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}
