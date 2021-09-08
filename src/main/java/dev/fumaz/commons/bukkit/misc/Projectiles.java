package dev.fumaz.commons.bukkit.misc;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public final class Projectiles {

    public static Entity launch(LivingEntity source, EntityType entityType) {
        return launch(source, 2, entityType);
    }

    public static Entity launch(LivingEntity source, double velocity, EntityType entityType) {
        return launch(source.getEyeLocation(), source.getEyeLocation().getDirection().multiply(velocity), entityType);
    }

    public static Entity launch(Location origin, Vector direction, EntityType entityType) {
        Entity entity = origin.getWorld().spawnEntity(origin, entityType);
        entity.setVelocity(direction);

        return entity;
    }

}
