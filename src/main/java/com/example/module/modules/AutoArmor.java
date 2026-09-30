package com.example.module.modules;

import com.example.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;

public class AutoArmor extends Module {
    public AutoArmor() {
        super("AutoArmor", "Equips best armor automatically", Category.COMBAT);
    }

    @Override
    public void onTick() {
        var mc = MinecraftClient.getInstance();
        var player = mc.player;
        var interaction = mc.interactionManager;
        if (player == null || interaction == null || player.currentScreenHandler != player.playerScreenHandler) return;

        int[] armorSlots = {5, 6, 7, 8};
        for (int armorSlot : armorSlots) {
            ItemStack current = player.getInventory().getStack(armorSlot);
            int bestSlot = -1;
            double bestScore = score(current);

            for (int i = 9; i < 36; i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (!(stack.getItem() instanceof ArmorItem armor)) continue;

                int neededSlot = armorSlot - 5;
                int typeOrdinal = switch (armor.getType()) {
                    case HELMET -> 0; case CHESTPLATE -> 1;
                    case LEGGINGS -> 2; case BOOTS -> 3;
                };
                if (typeOrdinal != neededSlot) continue;

                double s = score(stack);
                if (s > bestScore) { bestScore = s; bestSlot = i; }
            }

            if (bestSlot != -1) {
                interaction.clickSlot(player.currentScreenHandler.syncId, bestSlot, 0, SlotActionType.PICKUP, player);
                interaction.clickSlot(player.currentScreenHandler.syncId, armorSlot, 0, SlotActionType.PICKUP, player);
                interaction.clickSlot(player.currentScreenHandler.syncId, bestSlot, 0, SlotActionType.PICKUP, player);
            }
        }
    }

    private double score(ItemStack stack) {
        if (stack.isEmpty()) return -1;
        if (!(stack.getItem() instanceof ArmorItem armor)) return -1;
        double score = armor.getProtection();
        score += EnchantmentHelper.getLevel(Enchantments.PROTECTION, stack) * 2;
        score += EnchantmentHelper.getLevel(Enchantments.BLAST_PROTECTION, stack) * 1.5;
        score += armor.getToughness() * 1.5;
        if ((stack.getMaxDamage() - stack.getDamage()) < 30) score -= 5;
        return score;
    }
}
