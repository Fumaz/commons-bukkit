package dev.fumaz.commons.bukkit.math;

import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

public final class Locations {

    private Locations() {

    }

    /**
     * Checks if a given location is between two locations
     *
     * @param location the location
     * @param former   the first bound
     * @param latter   the second bound
     * @return true if the location is between former and latter, false otherwise
     */
    public static boolean isInAABB(Location location, Location former, Location latter) {
        BoundingBox box = BoundingBox.of(former, latter);

        return box.contains(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Checks if a given location is inside a sphere
     *
     * @param location the location
     * @param origin   the sphere's origin
     * @param radius   the radius of the sphere
     * @return true if the location is in the sphere, false otherwise
     */
    public static boolean isInSphere(Location location, Location origin, double radius) {
        return location.toVector().isInSphere(origin.toVector(), radius);
    }

}
