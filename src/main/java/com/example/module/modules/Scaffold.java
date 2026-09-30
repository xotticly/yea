package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.BoolSetting;
import com.example.settings.ModeSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    private final ModeSetting tower = new ModeSetting("Tower", "None", "None", "Jump", "Motion");
    private final BoolSetting swing = new BoolSetting("Swing", true);
    private final BoolSetting onlyBlocks = new BoolSetting("Blocks Only", true);

    public Scaffold() {
        super("Scaffold", "Places blocks under you", Category.MOVEMENT);
        addSetting(tower);
        addSetting(swing);
        addSetting(onlyBlocks);
    }

    @Override
    public void onTick() {
        var client = MinecraftClient.getInstance();
        var player = client.player;
        var interaction = client.interactionManager;
        if (player == null || interaction == null) return;

        BlockPos below = player.getBlockPos().down();
        if (!client.world.getBlockState(below).isAir()) return;

        int oldSlot = player.getInventory().selectedSlot;
        for (int i = 0; i < 9; i++) {
            var stack = player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;
            if (onlyBlocks.getValue() && !(stack.getItem() instanceof BlockItem)) continue;

            player.getInventory().selectedSlot = i;
            interaction.interactBlock(player, Hand.MAIN_HAND,
                    new BlockHitResult(Vec3d.ofCenter(below), Direction.UP, below, false));
            if (swing.getValue()) player.swingHand(Hand.MAIN_HAND);
            player.getInventory().selectedSlot = oldSlot;
            break;
        }

        if (!tower.getMode().equals("None") && client.options.jumpKey.isPressed()) {
            if (tower.getMode().equals("Jump")) {
                if (player.isOnGround()) player.jump();
            } else {
                player.setVelocity(player.getVelocity().x, 0.42, player.getVelocity().z);
            }
        }
    }
}
