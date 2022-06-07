package dev.fumaz.commons.bukkit.world;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.fumaz.commons.bukkit.interfaces.PluginListener;
import dev.fumaz.commons.bukkit.teams.ColoredTeams;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a "glowing" block.<br>
 * Since in vanilla minecraft only entities can have the glowing effect,<br>
 * this is achieved by summoning an invisible shulker entity inside the block<br>
 * and applying the glowing effect to that entity.<br>
 * There's a few problems with this implementation, like for example the shulker's head<br>
 * will always be visible, even when the entity itself is invisible.<br>
 * So this class shouldn't be used for translucent blocks.
 */
public class GlowingBlock implements PluginListener {

    private final JavaPlugin plugin;
    private final Block block;
    private final Entity entity;

    private GlowingBlock(JavaPlugin plugin, Block block, Entity entity) {
        this.plugin = plugin;
        this.block = block;
        this.entity = entity;

        register(plugin);
    }

    /**
     * Creates a glowing block from the specified {@link Block} and glowing {@link ChatColor}
     *
     * @param plugin the plugin this block belongs to
     * @param block  the block that is glowing
     * @param color  the color of the glow
     * @return the glowing block
     */
    public static GlowingBlock create(JavaPlugin plugin, Block block, ChatColor color) {
        return create(plugin, block)
                .color(color);
    }

    /**
     * Creates a glowing block from the specified {@link Block}
     *
     * @param plugin the plugin this block belongs to
     * @param block  the block that is glowing
     * @return the glowing block
     */
    public static GlowingBlock create(JavaPlugin plugin, Block block) {
        Entity entity = createEntity(block);

        return new GlowingBlock(plugin, block, entity);
    }

    /**
     * Creates the invisible entity needed to make the block glow
     *
     * @param block the block that is glowing
     * @return the invisible entity
     */
    private static Entity createEntity(Block block) {
        World world = block.getWorld();
        Shulker shulker = (Shulker) world.spawnEntity(block.getLocation().add(0.5, 0, 0.5), EntityType.SHULKER, CreatureSpawnEvent.SpawnReason.CUSTOM);
        shulker.setInvisible(true);
        shulker.setGravity(false);
        shulker.setAI(false);
        shulker.setInvulnerable(true);
        shulker.setRemoveWhenFarAway(false);
        shulker.setGlowing(true);

        return shulker;
    }

    /**
     * Removes the glow from this block, despawning the entity<br>
     * and unregistering all listeners
     */
    public void remove() {
        entity.remove();
        unregister();
    }

    /**
     * Sets the glow color
     *
     * @param color the color
     * @return the glowing block
     */
    public GlowingBlock color(ChatColor color) {
        ColoredTeams.setColor(entity, color);
        return this;
    }

    /**
     * @return the plugin this block belongs to
     */
    @Override
    public @NotNull JavaPlugin getPlugin() {
        return plugin;
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent event) {
        handleBlockEvent(event);
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        handleBlockEvent(event);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        handleBlockEvent(event);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        handleBlockEvent(event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        handleBlockEvent(event);
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent event) {
        handleBlockEvent(event);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() != getPlugin()) {
            return;
        }

        remove();
    }

    private void handleBlockEvent(BlockEvent event) {
        Location first = event.getBlock().getLocation();
        Location second = block.getLocation();

        if (first.getBlockX() != second.getBlockX() || first.getBlockY() != second.getBlockY() || first.getBlockZ() != second.getBlockZ() || !first.getWorld().equals(second.getWorld())) {
            return;
        }

        remove();
    }

}
