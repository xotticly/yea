package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Always sprint", Category.MOVEMENT);
        setKeybind(GLFW.GLFW_KEY_V);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        if (player != null && player.input.movementForward > 0 && !player.isSprinting()) {
            player.setSprinting(true);
        }
    }
}
