package dev.fumaz.commons.bukkit.world;

import org.bukkit.entity.Entity;

import java.util.Collection;

public final class Entities {

    /**
     * Returns all entities that are colliding with the entity.
     *
     * @param entity the entity
     * @return a collection of entities
     */
    public static Collection<Entity> getEntitiesInBoundingBox(Entity entity) {
        return BoundingBoxes.getEntities(entity.getWorld(), entity.getBoundingBox());
    }

}
