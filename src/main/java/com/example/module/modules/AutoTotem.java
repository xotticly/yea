package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", "Totem in offhand automatically", Category.COMBAT);
    }

    @Override
    public void onTick() {
        var player = MinecraftClient.getInstance().player;
        var interaction = MinecraftClient.getInstance().interactionManager;
        if (player == null || interaction == null) return;

        if (player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) return;

        for (int i = 9; i < 36; i++) {
            if (player.getInventory().getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                interaction.clickSlot(player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, player);
                interaction.clickSlot(player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, player);
                interaction.clickSlot(player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, player);
                break;
            }
        }
    }
}
