package com.example.module.modules;

import com.example.module.Module;
import com.example.settings.BoolSetting;
import com.example.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AutoCrystal extends Module {
    private final NumberSetting range = new NumberSetting("Range", 4.0, 2.0, 6.0, 0.5);
    private final NumberSetting delay = new NumberSetting("Delay (ms)", 200, 50, 1000, 50);
    private final BoolSetting safety = new BoolSetting("Check Self Damage", true);
    private long lastAction = 0;

    public AutoCrystal() {
        super("AutoCrystal", "Place + break crystals at mobs", Category.COMBAT);
        addSetting(range);
        addSetting(delay);
        addSetting(safety);
    }

    @Override
    public void onTick() {
        var mc = MinecraftClient.getInstance();
        var player = mc.player;
        if (player == null || mc.world == null) return;
        if (player.getMainHandStack().getItem() != Items.END_CRYSTAL) return;
        if (System.currentTimeMillis() - lastAction < delay.getInt()) return;

        LivingEntity target = null;
        double bestDist = range.getValue();
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity living) || living == player) continue;
            if (living instanceof net.minecraft.entity.player.PlayerEntity) continue;
            if (living.getHealth() <= 0) continue;
            double d = player.distanceTo(living);
            if (d < bestDist) { bestDist = d; target = living; }
        }
        if (target == null) return;

        BlockPos base = target.getBlockPos().down();
        if (!mc.world.getBlockState(base).isSolid()) return;
        if (safety.getValue() && target.distanceTo(player) < 3.0) return;

        Vec3d hitPos = Vec3d.ofCenter(base);
        mc.interactionManager.interactBlock(player, Hand.MAIN_HAND,
                new BlockHitResult(hitPos, Direction.UP, base, false));
        lastAction = System.currentTimeMillis();

        for (Entity e : mc.world.getEntities()) {
            if (e instanceof EndCrystalEntity crystal && crystal.squaredDistanceTo(hitPos) < 4) {
                mc.interactionManager.attackEntity(player, crystal);
                player.swingHand(Hand.MAIN_HAND);
                break;
            }
        }
    }
}
