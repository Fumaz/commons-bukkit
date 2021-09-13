package dev.fumaz.commons.bukkit.localization;

import dev.fumaz.commons.bukkit.text.ColorFormatting;
import dev.fumaz.commons.math.Randoms;
import dev.fumaz.commons.math.Romans;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility class to localize enchantment names
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class Enchantments {

    private Enchantments() {
    }

    /**
     * Returns the english localized name of an {@link Enchantment}
     *
     * @param enchantment the {@link Enchantment}
     * @return the localized name
     */
    public static String getLocalizedName(@NotNull Enchantment enchantment) {
        return NamespacedKeys.getDisplayName(enchantment.getKey());
    }

    /**
     * Returns an {@link Enchantment} from its localized name
     *
     * @param name the localized name of the enchantment
     * @return the {@link Enchantment}
     */
    public static Enchantment fromLocalizedName(@NotNull String name) {
        name = name.toLowerCase().replace(" ", "_");
        NamespacedKey key = NamespacedKey.minecraft(name);

        return Enchantment.getByKey(key);
    }

    /**
     * Returns a string representation of an enchantment, useful for item lores
     *
     * @param enchantment the enchantment
     * @param level       the level
     * @return the string representation
     */
    public static String toLore(@NotNull Enchantment enchantment, int level) {
        ChatColor color = enchantment.isCursed() ? ChatColor.RED : ChatColor.GRAY;

        return ColorFormatting.MAGIC_PREFIX + color + getLocalizedName(enchantment) + " " + level;
    }

    /**
     * Returns a string representation of an enchantment, with its level as a roman numeral
     *
     * @param enchantment the enchantment
     * @param level       the level
     * @return the string representation
     */
    public static String toRomanLore(@NotNull Enchantment enchantment, int level) {
        ChatColor color = enchantment.isCursed() ? ChatColor.RED : ChatColor.GRAY;

        return ColorFormatting.MAGIC_PREFIX + color + getLocalizedName(enchantment) + " " + Romans.toRoman(level);
    }

    // todo fix duplicate code
    public static ItemStack fixLore(@NotNull ItemStack itemStack) {
        List<String> lore = itemStack.getLore() != null ? new ArrayList<>(itemStack.getLore()) : new ArrayList<>();
        removeEnchantmentsFromLore(lore);

        Map<Enchantment, Integer> enchantments = getAppliedEnchantments(itemStack);

        if (enchantments.isEmpty()) {
            itemStack.setLore(lore);
            return itemStack;
        }

        lore.add(ChatColor.GRAY + "");
        enchantments
                .entrySet()
                .stream()
                .map(entry -> toLore(entry.getKey(), entry.getValue()))
                .forEach(lore::add);

        itemStack.setLore(lore);
        return itemStack;
    }

    public static ItemStack fixRomanLore(@NotNull ItemStack itemStack) {
        List<String> lore = itemStack.getLore() != null ? new ArrayList<>(itemStack.getLore()) : new ArrayList<>();
        removeEnchantmentsFromLore(lore);

        Map<Enchantment, Integer> enchantments = getAppliedEnchantments(itemStack);

        if (enchantments.isEmpty()) {
            itemStack.setLore(lore);
            return itemStack;
        }

        lore.add(ChatColor.GRAY + "");
        enchantments
                .entrySet()
                .stream()
                .map(entry -> toRomanLore(entry.getKey(), entry.getValue()))
                .forEach(lore::add);

        itemStack.setLore(lore);
        return itemStack;
    }

    public static Map<Enchantment, Integer> getAppliedEnchantments(@Nullable ItemStack item) {
        if (item == null) {
            return Collections.emptyMap();
        }

        Map<Enchantment, Integer> enchantments = new HashMap<>(item.getEnchantments());

        if (item.getItemMeta() instanceof EnchantmentStorageMeta) {
            enchantments.putAll(((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants());
        }

        return enchantments;
    }

    public static List<Enchantment> getSuitableEnchantments(@Nullable ItemStack item) {
        if (item == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(Enchantment.values())
                .filter(enchantment -> !enchantment.isCursed())
                .filter(enchantment -> enchantment.canEnchantItem(item) || item.getType() == Material.ENCHANTED_BOOK)
                .collect(Collectors.toList());
    }

    public static List<Enchantment> getGoodEnchantments() {
        return Arrays.stream(Enchantment.values())
                .filter(enchantment -> !enchantment.isCursed())
                .collect(Collectors.toList());
    }

    public static void enchantRandomly(@Nullable ItemStack item, int level) {
        Enchantment enchantment = Randoms.choice(getSuitableEnchantments(item));

        if (enchantment == null) {
            return;
        }

        item.addUnsafeEnchantment(enchantment, level);
    }

    public static void enchantRandomly(@Nullable ItemStack item) {
        Enchantment enchantment = Randoms.choice(getSuitableEnchantments(item));

        if (enchantment == null) {
            return;
        }

        item.addUnsafeEnchantment(enchantment, item.getEnchantmentLevel(enchantment) + 1);
    }

    private static List<String> removeEnchantmentsFromLore(@NotNull List<String> lore) {
        lore.removeIf(line -> {
            line = ChatColor.stripColor(line);

            List<String> words = Arrays.asList(line.split(" "));
            if (words.size() > 1) {
                words = words.subList(0, words.size() - 1);
            }

            line = String.join(" ", words);

            return fromLocalizedName(line) != null;
        });

        if (!lore.isEmpty() && StringUtils.isBlank(lore.get(lore.size() - 1))) {
            lore.remove(lore.size() - 1);
        }

        return lore;
    }

}
