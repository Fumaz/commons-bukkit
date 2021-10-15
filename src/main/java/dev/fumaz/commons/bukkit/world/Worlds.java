package dev.fumaz.commons.bukkit.world;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public final class Worlds {

    private Worlds() {
    }

    public static Block getHighestSolidBlockAt(World world, int x, int z) {
        Block highest = world.getHighestBlockAt(x, z);

        while (!highest.isSolid()) {
            highest = highest.getRelative(BlockFace.DOWN);
        }

        return highest;
    }

}
