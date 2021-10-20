package dev.fumaz.commons.bukkit.world;

import dev.fumaz.commons.bukkit.misc.Threads;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Orientable;
import org.bukkit.util.BoundingBox;

public class Portals {

    public static BoundingBox create(Location source, Axis axis, Material frame, boolean negative, boolean lit) {
        Threads.catchAsync("portal generation");

        int offsetX = (negative ? 1 : -1) * (axis == Axis.X ? 1 : 0) * 3;
        int offsetZ = (negative ? 1 : -1) * (axis == Axis.Z ? 1 : 0) * 3;

        BoundingBox box = new BoundingBox(
                source.getBlockX(),
                source.getBlockY(),
                source.getBlockZ(),

                source.getBlockX() + offsetX,
                source.getBlockY() + 4,
                source.getBlockZ() + offsetZ
        );

        BoundingBoxes.iterateBlocks(source.getWorld(), box, block -> {
            if (BoundingBoxes.isSurface(box, block.getLocation().toVector())) {
                block.setType(frame, false);
            } else if (lit) {
                block.setType(Material.NETHER_PORTAL, false);

                Orientable orientable = (Orientable) block.getBlockData();
                orientable.setAxis(axis);

                block.setBlockData(orientable, false);
            }
        });

        return box;
    }

    public static BoundingBox create(Location source, Axis axis, boolean negative, boolean lit) {
        return create(source, axis, Material.OBSIDIAN, negative, lit);
    }

}
