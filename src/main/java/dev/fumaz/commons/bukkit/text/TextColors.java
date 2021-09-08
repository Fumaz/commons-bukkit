package dev.fumaz.commons.bukkit.text;

import dev.fumaz.commons.bukkit.math.Vectors;
import dev.fumaz.commons.math.Randoms;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Work with colors!
 *
 * @author Fumaz
 * @version 1.0
 * @since 1.0
 */
public final class TextColors {

    private TextColors() {
    }

    /**
     * Returns a rainbow version of a string
     *
     * @param text       the text
     * @param increment  the increment
     * @param formatting any {@link ChatColor}
     * @return the rainbow string
     */
    public static String rainbow(String text, int increment, ChatColor... formatting) {
        int hue = Randoms.nextInt(361);

        return rainbow(text, hue, increment, formatting);
    }

    /**
     * Returns a rainbow version of a string, starting from a specified hue
     *
     * @param text       the text
     * @param hue        the hue
     * @param increment  the increment
     * @param formatting any {@link ChatColor}
     * @return the rainbow string
     */
    public static String rainbow(String text, int hue, int increment, ChatColor... formatting) {
        StringBuilder builder = new StringBuilder();

        for (char c : text.toCharArray()) {
            ChatColor color = ChatColor.of(Color.getHSBColor(hue / 360F, 1F, 1F));
            String formats = Arrays.stream(formatting)
                    .map(ChatColor::toString)
                    .collect(Collectors.joining());

            builder.append(color).append(formats).append(c);

            hue += increment; // In the future, add a calculation to figure out how to make the rainbow look good on strings of any length

            if (hue > 360) {
                hue = 0;
            }
        }

        return builder.toString();
    }

    /**
     * Returns a string colored with a gradient between two colors
     *
     * @param text       the text
     * @param from       the starting {@link Color}
     * @param to         the ending {@link Color}
     * @param formatting any {@link ChatColor}
     * @return the gradient string
     */
    public static String gradient(String text, Color from, Color to, ChatColor... formatting) {
        int length = text.length();

        double rStep = Math.abs((double) (from.getRed() - to.getRed()) / (double) length);
        double gStep = Math.abs((double) (from.getGreen() - to.getGreen()) / (double) length);
        double bStep = Math.abs((double) (from.getBlue() - to.getBlue()) / (double) length);

        if (from.getRed() > to.getRed()) {
            rStep = -rStep;
        }

        if (from.getGreen() > to.getGreen()) {
            gStep = -gStep;
        }

        if (from.getBlue() > to.getBlue()) {
            bStep = -bStep;
        }

        text = text.replaceAll("<\\$#[A-Fa-f0-9]{6}>", ""); // I genuinely have no idea what this does, or why its here
        text = text.replace("", "<$>");

        Color current = new Color(from.getRGB());
        for (int index = 0; index <= length; ++index) {
            int red = (int) Math.round((double) current.getRed() + rStep);
            int green = (int) Math.round((double) current.getGreen() + gStep);
            int blue = (int) Math.round((double) current.getBlue() + bStep);

            if (red > 255) {
                red = 255;
            }

            if (red < 0) {
                red = 0;
            }

            if (green > 255) {
                green = 255;
            }

            if (green < 0) {
                green = 0;
            }

            if (blue > 255) {
                blue = 255;
            }

            if (blue < 0) {
                blue = 0;
            }

            current = new Color(red, green, blue);
            String hex = "#" + Integer.toHexString(current.getRGB()).substring(2);
            String formats = Arrays.stream(formatting)
                    .map(ChatColor::toString)
                    .collect(Collectors.joining());

            text = text.replaceFirst("<\\$>", "" + ChatColor.of(hex) + formats);
        }

        return text;
    }

}
