package dev.fumaz.commons.bukkit.misc;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Interactions {

    private Interactions() {
    }

    public static boolean isRightClick(PlayerInteractEvent event) {
        return isRightClick(event.getAction());
    }

    public static boolean isLeftClick(PlayerInteractEvent event) {
        return isLeftClick(event.getAction());
    }

    public static boolean isClick(PlayerInteractEvent event) {
        return isClick(event.getAction());
    }

    public static ClickType getClickType(PlayerInteractEvent event) {
        return getClickType(event.getAction());
    }

    public static boolean isRightClick(@Nullable Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    public static boolean isLeftClick(@Nullable Action action) {
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
    }

    public static boolean isClick(@Nullable Action action) {
        return isRightClick(action) || isLeftClick(action);
    }

    @NotNull
    public static ClickType getClickType(@Nullable Action action) {
        if (isRightClick(action)) {
            return ClickType.RIGHT;
        }

        if (isLeftClick(action)) {
            return ClickType.LEFT;
        }

        return ClickType.NONE;
    }

}
