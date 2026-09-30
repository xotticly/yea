package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;

public class Fullbright extends Module {
    private double oldGamma = 1.0;

    public Fullbright() {
        super("Fullbright", "See in the dark", Category.RENDER);
    }

    @Override
    public void onEnable() {
        var options = MinecraftClient.getInstance().options;
        oldGamma = options.getGamma().getValue();
        options.getGamma().setValue(10.0);
    }

    @Override
    public void onDisable() {
        MinecraftClient.getInstance().options.getGamma().setValue(oldGamma);
    }
}
