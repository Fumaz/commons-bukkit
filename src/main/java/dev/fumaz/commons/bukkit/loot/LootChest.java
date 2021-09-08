package dev.fumaz.commons.bukkit.loot;

import dev.fumaz.commons.bukkit.interfaces.ListenerItem;
import dev.fumaz.commons.bukkit.item.ItemBuilder;
import dev.fumaz.commons.bukkit.localization.NamespacedKeys;
import dev.fumaz.commons.localization.Enums;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LootChest implements ListenerItem {

    private final JavaPlugin plugin;
    private final LootTable table;
    private final String displayName;

    public LootChest(JavaPlugin plugin, LootTable table, String displayName) {
        this.plugin = plugin;
        this.table = table;
        this.displayName = displayName;

        register(plugin);
    }

    public LootChest(JavaPlugin plugin, LootTables table, ChatColor color) {
        this(plugin, table.getLootTable(), color + Enums.getDisplayName(table));
    }

    public LootChest(JavaPlugin plugin, LootTables table) {
        this(plugin, table, ChatColor.WHITE);
    }

    @Override
    public @NotNull ItemStack getItemStack() {
        return ItemBuilder.of(Material.CHEST)
                .displayName(displayName + " Loot Chest")
                .addPersistentByte(getKey())
                .glow()
                .build();
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return NamespacedKeys.create(plugin, "loot_chest_" + table.getKey().getKey());
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!isItem(event.getItemInHand())) {
            return;
        }

        Block block = event.getBlockPlaced();
        Chest chest = (Chest) block.getState();

        LootContext context = new LootContext.Builder(chest.getLocation()).build();
        table.fillInventory(chest.getBlockInventory(), new Random(), context);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() != plugin) {
            return;
        }

        unregister();
    }

}
