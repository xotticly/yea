package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.BoolSetting;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class AutoEat extends Module {
    private final NumberSetting hunger = new NumberSetting("Hunger Threshold", 14, 1, 20, 1);
    private final BoolSetting pauseWhenCombat = new BoolSetting("Pause in Combat", true);
    private int oldSlot = -1;

    public AutoEat() {
        super("AutoEat", "Eats when hungry", Category.MISC);
        addSetting(hunger);
        addSetting(pauseWhenCombat);
    }

    @Override
    public void onTick() {
        var mc = MinecraftClient.getInstance();
        var player = mc.player;
        if (player == null || mc.currentScreen != null) return;

        if (player.getHungerManager().getFoodLevel() >= 20
                || (pauseWhenCombat.getValue() && mc.world != null
                    && !mc.world.getEntitiesByClass(net.minecraft.entity.mob.HostileEntity.class,
                        player.getBoundingBox().expand(6), e -> true).isEmpty())) {
            if (oldSlot != -1) {
                player.getInventory().selectedSlot = oldSlot;
                oldSlot = -1;
                mc.options.useKey.setPressed(false);
            }
            return;
        }

        if (oldSlot == -1) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem().getFoodComponent() != null) {
                    oldSlot = player.getInventory().selectedSlot;
                    player.getInventory().selectedSlot = i;
                    break;
                }
            }
            if (oldSlot == -1) return;
        }

        mc.options.useKey.setPressed(true);
        mc.interactionManager.interactItem(player, Hand.MAIN_HAND);
    }

    @Override
    public void onDisable() {
        var mc = MinecraftClient.getInstance();
        if (mc.player != null && oldSlot != -1) {
            mc.player.getInventory().selectedSlot = oldSlot;
            oldSlot = -1;
        }
        mc.options.useKey.setPressed(false);
    }
}
