package com.example;

import com.example.module.ModuleManager;
import com.example.ui.ClickGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Client implements ClientModInitializer {
    public static final String NAME = "MyClient";
    public static final String VERSION = "1.0";
    public static ModuleManager moduleManager;
    public static ClickGui clickGui;

    private static KeyBinding guiKey;

    @Override
    public void onInitializeClient() {
        moduleManager = new ModuleManager();
        moduleManager.init();
        clickGui = new ClickGui();

        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.myclient.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.myclient"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.wasPressed()) {
                client.setScreen(clickGui);
            }
            moduleManager.onTick();
        });

        System.out.println("[" + NAME + " " + VERSION + "] Loaded");
    }
}
