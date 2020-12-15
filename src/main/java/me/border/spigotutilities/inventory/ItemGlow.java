package me.border.spigotutilities.inventory;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class ItemGlow extends Enchantment {
    public ItemGlow(NamespacedKey id) {
        super(id);
    }

    public boolean canEnchantItem(ItemStack arg0) {
        return false;
    }

    public boolean conflictsWith(Enchantment arg0) {
        return false;
    }

    public EnchantmentTarget getItemTarget() {
        return null;
    }

    public boolean isTreasure() {
        return false;
    }

    public boolean isCursed() {
        return false;
    }

    public int getMaxLevel() {
        return 0;
    }

    public String getName() {
        return "Glow";
    }

    public int getStartLevel() {
        return 0;
    }
}