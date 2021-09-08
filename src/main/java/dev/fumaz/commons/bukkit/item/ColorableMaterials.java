package dev.fumaz.commons.bukkit.item;

import org.bukkit.inventory.ItemStack;

public enum ColorableMaterials {
    STAINED_GLASS_PANE,
    STAINED_GLASS,
    WOOL,
    CARPET,
    STAINED_HARDENED_CLAY,
    CONCRETE,
    CONCRETE_POWDER,
    TERRACOTTA,
    BANNER;

    public ItemStack withColor(MaterialColors color) {
        return color.toItem(this);
    }

}
