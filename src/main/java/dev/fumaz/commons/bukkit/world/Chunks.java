package dev.fumaz.commons.bukkit.world;

import org.bukkit.Chunk;
import org.bukkit.block.Block;

import java.util.function.Consumer;

public final class Chunks {

    private Chunks() {
    }

    /**
     * Consumes all blocks in a chunk
     *
     * @param chunk    the chunk
     * @param consumer the consumer
     */
    public static void iterateBlocks(Chunk chunk, Consumer<Block> consumer) {
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < chunk.getWorld().getMaxHeight(); ++y) {
                for (int z = 0; z < 16; ++z) {
                    consumer.accept(chunk.getBlock(x, y, z));
                }
            }
        }
    }

}
