package dev.fumaz.commons.bukkit.localization;

import org.apache.commons.lang.WordUtils;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class PotionEffects {

    private PotionEffects() {
    }

    /**
     * Returns the english localized name of a {@link PotionEffectType}
     *
     * @param effectType the {@link PotionEffectType}
     * @return the localized name
     */
    public static String getLocalizedName(PotionEffectType effectType) {
        return WordUtils.capitalizeFully(effectType.getName().replace("_", " "));
    }

}
