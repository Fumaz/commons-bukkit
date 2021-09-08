package dev.fumaz.commons.bukkit.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum MaterialColors {
    WHITE,
    ORANGE,
    MAGENTA,
    LIGHT_BLUE,
    YELLOW,
    LIME,
    PINK,
    GRAY,
    LIGHT_GRAY,
    CYAN,
    PURPLE,
    BLUE,
    BROWN,
    GREEN,
    RED,
    BLACK;

    @Nullable
    @SuppressWarnings("deprecation")
    public static MaterialColors fromItem(ItemStack item) {
        MaterialColors color = Arrays.stream(MaterialColors.values())
                .filter(c -> item.getType().name().startsWith(c.name()))
                .findFirst()
                .orElse(null);

        if (color != null) {
            return color;
        }

        short data = item.getDurability();

        if (data < 0 || data >= values().length) {
            return null;
        }

        return values()[data];
    }

    @NotNull
    @SuppressWarnings("deprecation")
    public ItemStack toItem(ColorableMaterials colorable) {
        Material material = Material.matchMaterial(name() + "_" + colorable.name());

        if (material != null) {
            return new ItemStack(material);
        }

        material = Material.matchMaterial(colorable.name());

        if (material == null) {
            return new ItemStack(Material.AIR);
        }

        return new ItemStack(material, 1, (short) ordinal());
    }

}
