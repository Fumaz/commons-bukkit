package dev.fumaz.commons.bukkit.item;

import com.google.common.collect.Lists;
import dev.fumaz.commons.bukkit.interfaces.Item;
import dev.fumaz.commons.bukkit.math.Vectors;
import dev.fumaz.commons.math.Randoms;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Stores a list of {@link ItemStack} and returns a random one
 * every time {@link RandomItem#getItemStack()} is called
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public class RandomItem implements Item {

    private final List<ItemStack> items;

    public RandomItem(ItemStack... items) {
        this(Lists.newArrayList(items));
    }

    public RandomItem(@NotNull List<ItemStack> items) {
        this.items = items;
    }

    @Override
    public @NotNull ItemStack getItemStack() {
        return Objects.requireNonNull(Randoms.choice(items));
    }

    public void add(ItemStack... items) {
        this.items.addAll(Arrays.asList(items));
    }

    public void remove(ItemStack... items) {
        this.items.removeAll(Arrays.asList(items));
    }

}
