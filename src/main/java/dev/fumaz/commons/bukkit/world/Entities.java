package dev.fumaz.commons.bukkit.world;

import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.stream.Collectors;

public class Entities {

    public static Collection<Entity> getEntitiesInBoundingBox(Entity entity) {
        BoundingBox boundingBox = entity.getBoundingBox().clone();

        return entity.getWorld().getEntities()
                .stream()
                .filter(target -> boundingBox.overlaps(target.getBoundingBox()))
                .collect(Collectors.toList());
    }

}
