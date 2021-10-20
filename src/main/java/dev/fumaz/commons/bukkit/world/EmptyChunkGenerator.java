package dev.fumaz.commons.bukkit.world;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A ChunkGenerator that always generates air.
 */
public class EmptyChunkGenerator extends ChunkGenerator {

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return Collections.emptyList();
    }

    public boolean canSpawn(@NotNull World world, int x, int z) {
        return true;
    }

    public byte[] generate(World world, Random rand, int x, int z) {
        return new byte['耀'];
    }

}
