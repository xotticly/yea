package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", "Best tool for the block", Category.MISC);
    }

    @Override
    public void onTick() {
        var mc = MinecraftClient.getInstance();
        var player = mc.player;
        if (player == null || mc.crosshairTarget == null
                || mc.crosshairTarget.getType() != net.minecraft.util.hit.HitResult.Type.BLOCK) return;
        if (!mc.options.attackKey.isPressed()) return;

        var blockPos = ((net.minecraft.util.hit.BlockHitResult) mc.crosshairTarget).getBlockPos();
        if (mc.world == null) return;
        BlockState state = mc.world.getBlockState(blockPos);

        int bestSlot = -1;
        float bestSpeed = 1.0f;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getStack(i);
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }
        if (bestSlot != -1) player.getInventory().selectedSlot = bestSlot;
    }
}
