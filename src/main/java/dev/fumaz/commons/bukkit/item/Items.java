package dev.fumaz.commons.bukkit.item;

import org.bukkit.inventory.ItemStack;

public final class Items {

    /**
     * Checks for equality between two ItemStacks without bothering
     * to check for durability, useful for tools and armor
     *
     * @param former the first {@link ItemStack}
     * @param latter the second {@link ItemStack}
     * @return whether the items are similar
     */
    public static boolean equal(ItemStack former, ItemStack latter) {
        if (former.isSimilar(latter)) {
            return true;
        }

        ItemStack clonedFormer = former.clone();
        clonedFormer.setDurability(latter.getDurability()); // Dang it spigot

        return clonedFormer.isSimilar(latter);
    }

}
