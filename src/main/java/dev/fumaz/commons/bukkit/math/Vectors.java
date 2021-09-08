package dev.fumaz.commons.bukkit.math;

import dev.fumaz.commons.math.Randoms;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

/**
 * All utilities that involve random number generation will be found here
 * Please note, most of the methods behave according to {@link ThreadLocalRandom}
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class Vectors {

    private Vectors() {
    }

    /**
     * Returns a pseudorandom {@link Vector} with x, y and z values between -1.0 and 1.0
     *
     * @return the {@link Vector}
     */
    public static Vector nextVector() {
        double velocityX = Randoms.nextDouble();
        double velocityY = Randoms.nextDouble();
        double velocityZ = Randoms.nextDouble();

        if (Randoms.nextBoolean()) {
            velocityX = -velocityX;
        }

        if (Randoms.nextBoolean()) {
            velocityY = -velocityY;
        }

        if (Randoms.nextBoolean()) {
            velocityZ = -velocityZ;
        }

        return new Vector(velocityX, velocityY, velocityZ);
    }

    /**
     * Returns a pseudorandom {@link Vector} with x and z values between -1.0 and 1.0
     * and a y value between 0.0 and 1.0
     *
     * @return the {@link Vector}
     */
    public static Vector nextPositiveYVector() {
        Vector vector = nextVector();

        if (vector.getY() < 0) {
            vector.setY(-vector.getY());
        }

        return vector;
    }

    /**
     * Returns a pseudorandom {@link Vector} with values between the given ranges
     *
     * @param minX the minimum X value
     * @param maxX the maximum X value
     * @param minY the minimum Y value
     * @param maxY the maximum Y value
     * @param minZ the minimum Z value
     * @param maxZ the maximum Z value
     * @return the {@link Vector}
     */
    public static Vector nextVector(float minX, float maxX, float minY, float maxY, float minZ, float maxZ) {
        double velocityX = Randoms.nextDouble(minX, maxX);
        double velocityY = Randoms.nextDouble(minY, maxY);
        double velocityZ = Randoms.nextDouble(minZ, maxZ);

        return new Vector(velocityX, velocityY, velocityZ);
    }

}
