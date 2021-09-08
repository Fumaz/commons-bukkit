package dev.fumaz.commons.bukkit.math;

import org.bukkit.util.Vector;

import java.util.function.Consumer;

public final class Geometry {

    private Geometry() {
    }

    /**
     * Consumes points of a circle
     *
     * @param radius   the radius
     * @param consumer the consumer
     */
    public static void circle(double radius, Consumer<Vector> consumer) {
        circle(radius, 0.01, consumer);
    }

    /**
     * Consumes points of a circle with a certain distance (step)
     *
     * @param radius   the radius
     * @param step     the distance between two points of the circle (in radians)
     * @param consumer the consumer
     */
    public static void circle(double radius, double step, Consumer<Vector> consumer) {
        double radians = 0;
        double x;
        double y;

        while (radians < (Math.PI * 2)) {
            x = Math.cos(radians) * radius;
            y = Math.sin(radians) * radius;

            consumer.accept(new Vector(x, y, 0));
            radians += step;
        }
    }

    /**
     * Consumes points of a 2D square
     *
     * @param radius   the radius
     * @param consumer the consumer
     */
    public static void square(double radius, Consumer<Vector> consumer) {
        square(radius, 1, consumer);
    }

    /**
     * Consumes points of a 2D square
     *
     * @param radius   the radius
     * @param step     the distance between two points of the square
     * @param consumer the consumer
     */
    public static void square(double radius, double step, Consumer<Vector> consumer) {
        for (double x = -radius; x <= radius; x += step) {
            for (double y = -radius; y <= radius; y += step) {
                consumer.accept(new Vector(x, y, 0));
            }
        }
    }

    /**
     * Consumes points of a cube
     *
     * @param radius   the radius
     * @param consumer the consumer
     */
    public static void cube(double radius, Consumer<Vector> consumer) {
        cube(radius, 1, consumer);
    }

    /**
     * Consumes points of a cube
     *
     * @param radius   the radius
     * @param step     the distance between each point
     * @param consumer the consumer
     */
    public static void cube(double radius, double step, Consumer<Vector> consumer) {
        for (double x = -radius; x <= radius; x += step) {
            for (double y = -radius; y <= radius; y += step) {
                for (double z = -radius; z <= radius; z += step) {
                    consumer.accept(new Vector(x, y, z));
                }
            }
        }
    }

    /**
     * Consumes points of the edges of a box
     *
     * @param radius   the radius
     * @param consumer the consumer
     */
    public static void cubeSurface(double radius, Consumer<Vector> consumer) {
        cubeSurface(radius, 1, consumer);
    }

    /**
     * Consumes points of the edges of a box
     *
     * @param radius   the radius
     * @param step     the distance between points
     * @param consumer the consumer
     */
    public static void cubeSurface(double radius, double step, Consumer<Vector> consumer) {
        final Vector min = new Vector(-radius, -radius, -radius);
        final Vector max = new Vector(radius, radius, radius);

        cube(radius, step, vector -> {
            if (!isSurfaceOfCube(vector, min, max, step)) {
                return;
            }

            consumer.accept(vector);
        });
    }

    /**
     * Consumes points of a sphere
     *
     * @param radius   the radius
     * @param consumer the consumer
     */
    public static void sphere(double radius, Consumer<Vector> consumer) {
        sphere(radius, 1, consumer);
    }

    /**
     * Consumes points of a sphere with a certain distance (step)
     *
     * @param radius   the radius
     * @param step     the distance between two points
     * @param consumer the consumer
     */
    public static void sphere(double radius, double step, Consumer<Vector> consumer) {
        final Vector origin = new Vector(0, 0, 0);

        cube(radius, step, vector -> {
            if (!vector.isInSphere(origin, radius)) {
                return;
            }

            consumer.accept(vector);
        });
    }

    /**
     * Consumes points of a sphere's surface with a certain distance
     *
     * @param radius   the radius
     * @param step     the distance between two points
     * @param consumer the consumer
     */
    public static void sphereSurface(double radius, double step, Consumer<Vector> consumer) {
        final Vector origin = new Vector(0, 0, 0);

        for (double x = -radius; x <= radius; x += step) {
            for (double y = -radius; y <= radius; y += step) {
                for (double z = -radius; z <= radius; z += step) {
                    Vector vector = new Vector(x, y, z);

                    if (!isSurfaceOfSphere(vector, origin, step, radius)) {
                        continue;
                    }

                    consumer.accept(vector);
                }
            }
        }
    }

    /**
     * Consumes points of a line between two 2D points
     *
     * @param start    the start of the line (2D)
     * @param end      the end of the line (2D)
     * @param consumer the consumer
     */
    public static void line2D(Vector start, Vector end, Consumer<Vector> consumer) {
        line2D(start, end, 1, consumer);
    }

    /**
     * Consumes points of a line between two 2D points
     *
     * @param start    the start of the line (2D)
     * @param end      the end of the line (2D)
     * @param step     the distance between each point
     * @param consumer the consumer
     */
    public static void line2D(Vector start, Vector end, double step, Consumer<Vector> consumer) {
        double distanceX = end.getX() - start.getX();
        double distanceY = end.getY() - start.getY();

        double distance = end.distance(start);
        int points = (int) (distance / step);

        double stepX = distanceX / points;
        double stepY = distanceY / points;
        double x = start.getX();
        double y = start.getY();

        for (int i = 0; i < points; i++) {
            consumer.accept(new Vector(x, y, 0));
            x += stepX;
            y += stepY;
        }
    }

    /**
     * Consumes points of a line between two 3D points
     *
     * @param start    the start of the line (3D)
     * @param end      the end of the line (3D)
     * @param consumer the consumer
     */
    public static void line3D(Vector start, Vector end, Consumer<Vector> consumer) {
        line3D(start, end, 1, consumer);
    }

    /**
     * Consumes points of a line between two 3D points
     *
     * @param start    the start of the line (3D)
     * @param end      the end of the line (3D)
     * @param step     the distance between each point
     * @param consumer the consumer
     */
    public static void line3D(Vector start, Vector end, double step, Consumer<Vector> consumer) {
        double distanceX = end.getX() - start.getX();
        double distanceY = end.getY() - start.getY();
        double distanceZ = end.getZ() - start.getZ();

        double distance = end.distance(start);
        int points = (int) (distance / step);

        double stepX = distanceX / points;
        double stepY = distanceY / points;
        double stepZ = distanceZ / points;

        double x = start.getX();
        double y = start.getY();
        double z = start.getZ();

        for (int i = 0; i < points; i++) {
            consumer.accept(new Vector(x, y, z));
            x += stepX;
            y += stepY;
            z += stepZ;
        }
    }

    /**
     * Consumes points of a 3D helix
     *
     * @param height      the height of the helix
     * @param radius      the radius of the helix
     * @param radiansStep the step of the circle
     * @param heightStep  the step of the height
     * @param consumer    the consumer
     */
    public static void helix(double height, double radius, double radiansStep, double heightStep, Consumer<Vector> consumer) {
        for (double y = 0, radians = 0; y <= height; y += heightStep) {
            radians += radiansStep;
            double x = Math.cos(radians) * radius;
            double z = Math.sin(radians) * radius;

            consumer.accept(new Vector(x, y, z));
        }
    }

    /**
     * Checks if a 3D point is on the surface of a spherical radius, that is,
     * if a point is inside the radius but incrementing any coordinate by a certain step
     * makes it outside the radius
     *
     * @param vector the point
     * @param origin the sphere's origin
     * @param step   how much to increment each coordinate
     * @param radius the radius of the sphere
     * @return true if the point is on the surface of the sphere, false otherwise
     */
    private static boolean isSurfaceOfSphere(Vector vector, Vector origin, double step, double radius) {
        if (!vector.isInSphere(origin, radius)) {
            return false;
        }

        for (double s : new double[]{-step, step}) {
            Vector vectorX = vector.clone().add(new Vector(s, 0, 0));
            Vector vectorY = vector.clone().add(new Vector(0, s, 0));
            Vector vectorZ = vector.clone().add(new Vector(0, 0, s));

            if (!vectorX.isInSphere(origin, radius) || !vectorY.isInSphere(origin, radius) || !vectorZ.isInSphere(origin, radius)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a 3D point is on the surface of a 3D box, that is,
     * if a point is inside the box but incrementing any coordinate by a certain step
     * makes it outside the box
     *
     * @param vector the point
     * @param min    the minimum edge of the box
     * @param max    the maximum edge of the box
     * @param step   how much to increment each coordinate
     * @return true if the point is on the surface of the box, false otherwise
     */
    private static boolean isSurfaceOfCube(Vector vector, Vector min, Vector max, double step) {
        if (!vector.isInAABB(min, max)) {
            return false;
        }

        for (double s : new double[]{-step, step}) {
            Vector vectorX = vector.clone().add(new Vector(s, 0, 0));
            Vector vectorY = vector.clone().add(new Vector(0, s, 0));
            Vector vectorZ = vector.clone().add(new Vector(0, 0, s));

            if (!vectorX.isInAABB(min, max) || !vectorY.isInAABB(min, max) || !vectorZ.isInAABB(min, max)) {
                return true;
            }
        }

        return false;
    }

}
