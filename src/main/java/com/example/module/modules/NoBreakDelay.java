package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;

public class NoBreakDelay extends Module {
    public NoBreakDelay() {
        super("NoBreakDelay", "No break cooldown between blocks", Category.MISC);
    }

    @Override
    public void onTick() {
        var interaction = MinecraftClient.getInstance().interactionManager;
        if (interaction != null) interaction.blockBreakingCooldown = 0;
    }
}
