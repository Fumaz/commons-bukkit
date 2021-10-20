package dev.fumaz.commons.bukkit.world;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class BoundingBoxes {

    /**
     * Iterates over each integer coordinate of the {@link BoundingBox} with {@link Vector}s
     *
     * @param box      the box
     * @param consumer the consumer
     */
    public static void iterate(@NotNull BoundingBox box, @NotNull Consumer<Vector> consumer) {
        for (int x = (int) -box.getMinX(); x <= box.getMaxX(); ++x) {
            for (int y = (int) -box.getMinY(); y <= box.getMaxY(); ++y) {
                for (int z = (int) -box.getMinZ(); z <= box.getMaxZ(); ++z) {
                    consumer.accept(new Vector(x, y, z));
                }
            }
        }
    }

    /**
     * Iterates over each {@link Block} of the {@link BoundingBox} in the specified {@link World}
     *
     * @param world    the world
     * @param box      the box
     * @param consumer the consumer
     */
    public static void iterateBlocks(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<Block> consumer) {
        iterate(box, vector -> consumer.accept(world.getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ())));
    }

    /**
     * Iterates over the surface of the {@link BoundingBox} with {@link Vector}s.
     * A surface is considered any coordinate that is directly at the edges, but only if the dimension of that axis is more than 1.
     *
     * @param box      the box
     * @param consumer the consumer
     */
    public static void iterateSurface(@NotNull BoundingBox box, @NotNull Consumer<Vector> consumer) {
        iterate(box, vector -> {
            if (!isSurface(box, vector)) {
                return;
            }

            consumer.accept(vector);
        });
    }

    /**
     * Iterates over the surface of the {@link BoundingBox} with {@link Block}s in the specified {@link World}
     * A surface is considered any coordinate that is directly at the edges, but only if the dimension of that axis is more than 1.
     *
     * @param world    the world
     * @param box      the box
     * @param consumer the consumer
     */
    public static void iterateSurfaceBlocks(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<Block> consumer) {
        iterateSurface(box, vector -> consumer.accept(world.getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ())));
    }

    /**
     * Checks if a coordinate is on the surface of a {@link BoundingBox}.
     * A surface is considered any coordinate that is directly at the edges, but only if the dimension of that axis is more than 1.
     * *
     *
     * @param box the bounding box
     * @param x   x
     * @param y   y
     * @param z   z
     * @return true if it's on the surface, false otherwise
     */
    public static boolean isSurface(@NotNull BoundingBox box, int x, int y, int z) {
        final int maxX = (int) box.getMaxX();
        final int maxY = (int) box.getMaxY();
        final int maxZ = (int) box.getMaxZ();

        final int minX = (int) box.getMinX();
        final int minY = (int) box.getMinY();
        final int minZ = (int) box.getMinZ();

        final boolean compareX = maxX != minX;
        final boolean compareY = maxY != minY;
        final boolean compareZ = maxZ != minZ;

        return (compareX && (x == maxX || x == minX)) || (compareY && (y == maxY || y == minY)) || (compareZ && (z == maxZ || z == minZ));
    }

    /**
     * Checks if a coordinate is on the surface of a {@link BoundingBox}.
     * A surface is considered any coordinate that is directly at the edges, but only if the dimension of that axis is more than 1.
     * *
     *
     * @param box    the bounding box
     * @param vector the coordinate
     * @return true if it's on the surface, false otherwise
     */
    public static boolean isSurface(@NotNull BoundingBox box, @NotNull Vector vector) {
        return isSurface(box, vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    /**
     * Returns all {@link Entity}s inside a {@link BoundingBox} in the specified {@link World}
     *
     * @param world the world
     * @param box   the box
     * @return a collection of entities
     */
    @NotNull
    public static Collection<Entity> getEntities(@NotNull World world, @NotNull BoundingBox box) {
        return world.getEntities()
                .stream()
                .filter(entity -> box.overlaps(entity.getBoundingBox()))
                .collect(Collectors.toSet());
    }

    /**
     * Iterates all the {@link Entity}s inside a {@link BoundingBox} in the specified {@link World}
     *
     * @param world    the world
     * @param box      the box
     * @param consumer the consumer
     */
    public static void iterateEntities(@NotNull World world, @NotNull BoundingBox box, Consumer<Entity> consumer) {
        getEntities(world, box).forEach(consumer);
    }

}
