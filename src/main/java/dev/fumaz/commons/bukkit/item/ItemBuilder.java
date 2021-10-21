package dev.fumaz.commons.bukkit.item;

import com.google.common.collect.Lists;
import dev.fumaz.commons.bukkit.interfaces.Item;
import dev.fumaz.commons.bukkit.localization.Enchantments;
import dev.fumaz.commons.bukkit.localization.PotionEffects;
import dev.fumaz.commons.collection.FCollections;
import dev.fumaz.commons.math.Randoms;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A utility to create and edit {@link ItemStack}s easily
 *
 * @author Fumaz
 * @version 2.11
 * @since 1.0
 */
public class ItemBuilder {

    private final ItemStack itemStack;

    private ItemBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Creates a {@link ItemBuilder} with the specified {@link Material}, and the default {@code int} amount (1)
     *
     * @param material the {@link Material}
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder of(@NotNull Material material) {
        return of(material, 1);
    }

    /**
     * Creates a {@link ItemBuilder} with the specified {@link Material}, and {@code int} amount
     *
     * @param material the {@link Material}
     * @param amount   the amount
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder of(@NotNull Material material, int amount) {
        return copy(new ItemStack(material, amount));
    }

    /**
     * Creates an {@link ItemBuilder} from the specified {@link ItemStack}
     * The original {@link ItemStack} will not be mutated.
     *
     * @param itemStack the {@link ItemStack} to copy
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder copy(@NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack.clone());
    }

    /**
     * Creates an {@link ItemBuilder} that directly edits the specified {@link ItemStack}
     *
     * @param itemStack the {@link ItemStack} to edit
     * @return the {@link ItemBuilder}
     */
    public static ItemBuilder edit(@NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     * Feeds the {@link ItemStack} to a {@link Consumer<ItemStack>}, allowing for edits.
     *
     * @param consumer the {@link Consumer<ItemStack>}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder consume(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);

        return this;
    }

    /**
     * Feeds the {@link ItemStack}'s {@link ItemMeta} to a {@link Consumer<ItemMeta>}, allowing for edits.
     *
     * @param consumer the {@link Consumer<ItemMeta>}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder consumeMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = itemStack.getItemMeta();
        consumer.accept(meta);
        itemStack.setItemMeta(meta);

        return this;
    }

    /**
     * Feeds a subclass of the {@link ItemStack}'s {@link ItemMeta} to a {@link Consumer<T>}, allowing for edits.
     *
     * @param clazz    the subclass of {@link ItemMeta} to consume
     * @param consumer the {@link Consumer<T>}
     * @param <T>      the type of {@link ItemMeta}
     * @return the {@link ItemBuilder}
     */
    public <T extends ItemMeta> ItemBuilder consumeCustomMeta(Class<T> clazz, Consumer<T> consumer) {
        return consumeMeta(meta -> consumer.accept(clazz.cast(meta)));
    }

    /**
     * Feeds the {@link ItemStack}'s {@link BlockState} to a {@link Consumer}
     *
     * @param consumer the {@link Consumer}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder consumeBlockState(Consumer<BlockState> consumer) {
        return consumeCustomMeta(BlockStateMeta.class, meta -> {
            BlockState state = meta.getBlockState();

            consumer.accept(state);
            meta.setBlockState(state);
        });
    }

    /**
     * Feeds a subclass of the {@link ItemStack}'s {@link BlockState} to a {@link Consumer}
     *
     * @param clazz    the subclass of {@link BlockState} to consume
     * @param consumer the {@link Consumer}
     * @param <T>      the type of the {@link BlockState}
     * @return the {@link ItemBuilder}
     */
    public <T extends BlockState> ItemBuilder consumeCustomBlockState(Class<T> clazz, Consumer<T> consumer) {
        return consumeBlockState(state -> consumer.accept(clazz.cast(state)));
    }

    /**
     * Feeds the {@link ItemStack}'s {@link PersistentDataContainer} to a {@link Consumer<PersistentDataContainer>}, allowing for edits.
     *
     * @param consumer the {@link Consumer<PersistentDataContainer>}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder consumePersistentData(Consumer<PersistentDataContainer> consumer) {
        return consumeMeta(meta -> consumer.accept(meta.getPersistentDataContainer()));
    }

    /**
     * Sets the {@link ItemStack}'s {@link Material}
     *
     * @param material the {@link Material}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder material(@NotNull Material material) {
        return consume(item -> item.setType(material));
    }

    /**
     * Sets the {@link ItemStack}'s {@code int} amount
     *
     * @param amount the amount
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder amount(int amount) {
        return consume(item -> item.setAmount(amount));
    }

    /**
     * Sets the {@link ItemStack}'s {@link MaterialData}
     *
     * @param data the {@link MaterialData}
     * @return the {@link ItemBuilder}
     * @deprecated {@link MaterialData} is currently deprecated.
     */
    @Deprecated
    public ItemBuilder data(@NotNull MaterialData data) {
        return consume(item -> item.setData(data));
    }

    /**
     * Adds {@link ItemFlag}s to the {@link ItemStack}
     *
     * @param flags the {@link ItemFlag}s to add
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        return consume(item -> item.addItemFlags(flags));
    }

    /**
     * Removes {@link ItemFlag}s from the {@link ItemStack}
     *
     * @param flags the {@link ItemFlag}s to remove
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        return consume(item -> item.removeItemFlags(flags));
    }

    /**
     * Makes the {@link ItemStack} unbreakable, and hides the unbreakability
     *
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder unbreakable() {
        addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        return consumeMeta(meta -> meta.setUnbreakable(true));
    }

    /**
     * Makes the {@link ItemStack} glow, and hides its enchantments
     *
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder glow() {
        addItemFlags(ItemFlag.HIDE_ENCHANTS);
        enchant(Enchantment.OXYGEN, 1);

        return this;
    }

    /**
     * Adds a random {@link Enchantment} to the {@link ItemStack}, with a random {@code int} level
     *
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder enchantRandomly() {
        getRandomEnchantment(true)
                .ifPresent(enchantment -> {
                    int level = Randoms.nextInt(enchantment.getStartLevel(), enchantment.getMaxLevel() + 1);

                    enchant(enchantment, level);
                });

        return this;
    }

    /**
     * Adds a random {@link Enchantment} to the {@link ItemStack}, with the specified {@code int} level
     *
     * @param level the level of the enchantment
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder enchantRandomly(int level) {
        getRandomEnchantment(true)
                .ifPresent(enchantment -> enchant(enchantment, level));

        return this;
    }

    /**
     * Adds an {@link Enchantment} to the {@link ItemStack} with the specified {@code int} level
     *
     * @param enchantment the {@link Enchantment}
     * @param level       the level
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder enchant(@NotNull Enchantment enchantment, int level) {
        if (itemStack.getType() == Material.ENCHANTED_BOOK) {
            return storeEnchantment(enchantment, level);
        }

        return consume(item -> item.addUnsafeEnchantment(enchantment, level));
    }

    /**
     * Adds a set of {@link Enchantment}s to the {@link ItemStack}
     *
     * @param enchantments the map with the enchantments
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder enchant(@NotNull Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::enchant);
        return this;
    }

    /**
     * Stores an {@link Enchantment} in the {@link ItemStack}'s {@link EnchantmentStorageMeta} with the specified {@code int} level
     *
     * @param enchantment the {@link Enchantment}
     * @param level       the level
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder storeEnchantment(@NotNull Enchantment enchantment, int level) {
        return consumeCustomMeta(EnchantmentStorageMeta.class, meta -> meta.addStoredEnchant(enchantment, level, true));
    }

    /**
     * Stores a set of {@link Enchantment} in the {@link ItemStack}'s {@link EnchantmentStorageMeta}
     *
     * @param enchantments the map with the enchantments
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder storeEnchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::storeEnchantment);
        return this;
    }

    public ItemBuilder fixEnchantsLore() {
        Enchantments.fixLore(itemStack);
        return this;
    }

    public ItemBuilder fixRomanLore() {
        Enchantments.fixRomanLore(itemStack);
        return this;
    }

    /**
     * Sets the {@link ItemStack}'s display name
     *
     * @param displayName the display name
     * @return the {@link ItemBuilder}
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder displayName(@Nullable String displayName) {
        return consumeMeta(meta -> meta.setDisplayName(displayName));
    }

    /**
     * Sets the {@link ItemStack}'s lore
     *
     * @param lore the lore
     * @return the {@link ItemBuilder}
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder lore(@Nullable List<String> lore) {
        return consumeMeta(meta -> meta.setLore(lore));
    }

    /**
     * Adds lines to the {@link ItemStack}'s lore
     *
     * @param lines the lines to add
     * @return the {@link ItemBuilder}
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder addToLore(@NotNull List<String> lines) {
        return consumeMeta(meta -> meta.setLore(FCollections.merge(getLore(), lines)));
    }

    /**
     * Sets the {@link ItemStack}'s lore
     *
     * @param lore the lore
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder lore(String... lore) {
        return lore(Lists.newArrayList(lore));
    }

    /**
     * Adds lines to the {@link ItemStack}'s lore
     *
     * @param lines the lines to add
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder addToLore(String... lines) {
        return addToLore(Lists.newArrayList(lines));
    }

    /**
     * Sets the {@link ItemStack}'s damage
     *
     * @param damage the damage
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder damage(int damage) {
        return consumeMeta(meta -> ((Damageable) meta).setDamage(damage));
    }

    /**
     * Sets the {@link ItemStack}'s custom model data
     *
     * @param customModelData the custom model data
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder customModelData(int customModelData) {
        return consumeMeta(meta -> meta.setCustomModelData(customModelData));
    }

    /**
     * Adds a persistent data entry to the {@link ItemStack}
     *
     * @param key      the {@link NamespacedKey}
     * @param dataType the {@link PersistentDataType}
     * @param value    the data
     * @param <T>      the type of the data
     * @param <Z>      the type of the data
     * @return the {@link ItemBuilder}
     */
    public <T, Z> ItemBuilder addPersistentData(NamespacedKey key, PersistentDataType<T, Z> dataType, Z value) {
        return consumePersistentData(persistentDataContainer -> persistentDataContainer.set(key, dataType, value));
    }

    /**
     * Adds a persistent {@code byte} to the {@link ItemStack}
     *
     * @param key the {@link NamespacedKey}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder addPersistentByte(NamespacedKey key) {
        return addPersistentData(key, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * Sets the color and name of the {@link ItemStack} which <b>must</b> be a potion
     *
     * @param effectType the {@link PotionEffectType}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder potionType(PotionEffectType effectType) {
        return consumeCustomMeta(PotionMeta.class, meta -> {
            meta.setColor(effectType.getColor());
            meta.setDisplayName(ChatColor.WHITE + "Potion of " + PotionEffects.getLocalizedName(effectType));
        });
    }

    /**
     * Adds a custom potion effect to the {@link ItemStack} which <b>must</b> be a potion
     *
     * @param effect the {@link PotionEffects}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder addPotionEffect(PotionEffect effect) {
        return consumeCustomMeta(PotionMeta.class, meta -> {
            if (!hasPotionEffects()) {
                potionType(effect.getType());
            }

            meta.addCustomEffect(effect, true);
        });
    }

    /**
     * Adds a custom potion effect to the {@link ItemStack} which <b>must</b> be a potion
     *
     * @param effectType the {@link PotionEffectType}
     * @param seconds    the duration of the effect, in seconds
     * @param level      the level of the effect, starting from 1
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder addPotionEffect(PotionEffectType effectType, int seconds, int level) {
        return addPotionEffect(new PotionEffect(effectType, seconds * 20, level - 1));
    }

    /**
     * @return true if the {@link ItemStack} has at least one potion effect
     */
    public boolean hasPotionEffects() {
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

        return meta.hasColor();
    }

    /**
     * Sets the base potion data of the {@link ItemStack}
     *
     * @param potionData the {@link PotionData}
     * @return the {@link ItemBuilder}
     */
    public ItemBuilder basePotionData(PotionData potionData) {
        return consumeCustomMeta(PotionMeta.class, meta -> meta.setBasePotionData(potionData));
    }

    @SuppressWarnings("deprecation")
    public List<String> getLore() {
        return itemStack.getLore() != null ? itemStack.getLore() : new ArrayList<>();
    }

    /**
     * Builds the {@link ItemStack}
     *
     * @return the {@link ItemStack}
     */
    public ItemStack build() {
        return itemStack.clone();
    }

    @NotNull
    private Optional<Enchantment> getRandomEnchantment(boolean specific) {
        List<Enchantment> enchantments = Arrays.stream(Enchantment.values())
                .filter(enchantment -> !enchantment.isCursed())
                .filter(enchantment -> !specific || (enchantment.canEnchantItem(itemStack) || itemStack.getType() == Material.ENCHANTED_BOOK))
                .collect(Collectors.toList());

        return Optional.ofNullable(Randoms.choice(enchantments));
    }

}
