package dev.fumaz.commons.bukkit.text;

import com.google.common.primitives.Chars;
import dev.fumaz.commons.text.TextFormatting;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ColorFormatting {

    public static final char INTERNAL_COLOR_CODE = '§';
    public static final char EXTERNAL_COLOR_CODE = '&';
    public static final String HEART = "❤";
    public static final String MAGIC_PREFIX = ChatColor.MAGIC + "" + ChatColor.RESET;
    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "!" + ChatColor.GRAY + "] ";
    public static final String UNKNOWN_COMMAND = ChatColor.WHITE + "Unknown command. Type \"help\" for help.";
    public static final String PLAYER_ONLY_COMMAND = ChatColor.RED + "This command can only be used by players.";

    private static final int CENTER_PX = 154;

    private ColorFormatting() {
    }

    /**
     * Adds colors to a text with the default {@link ColorFormatting#EXTERNAL_COLOR_CODE}
     *
     * @param text the text
     * @return the colorized text
     */
    @Nullable
    public static String colorize(@Nullable String text) {
        if (text == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes(EXTERNAL_COLOR_CODE, text);
    }

    /**
     * Escapes colors from a text and turns them into {@link ColorFormatting#EXTERNAL_COLOR_CODE}
     *
     * @param text the text
     * @return the escaped text
     */
    @Nullable
    public static String decolorize(@Nullable String text) {
        if (text == null) {
            return null;
        }

        return text.replace(INTERNAL_COLOR_CODE, EXTERNAL_COLOR_CODE);
    }

    /**
     * Adds some formatting after every {@link ChatColor} present in the text
     * This is useful to make an already colored string bold, for example
     *
     * @param text       the text (won't be edited in place)
     * @param formatting the formatting to add
     * @return the formatted text
     */
    @Nullable
    public static String addFormatting(@Nullable String text, ChatColor... formatting) {
        if (text == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        boolean special = false;

        List<Character> characters = Chars.asList(text.toCharArray());
        for (int i = 0; i < characters.size(); i++) {
            char character = characters.get(i);

            builder.append(character);

            if (isColorCharacter(character)) {
                special = true;
                continue;
            }

            if (special) {
                special = false;

                // We need to check if there's another color afterwards or RGB colors are going to break
                boolean lastCharacter = (i == (characters.size() - 1));
                if (lastCharacter || !isColorCharacter(characters.get(i + 1))) {
                    for (ChatColor f : formatting) {
                        builder.append(f);
                    }
                }
            }
        }

        return builder.toString();
    }

    /**
     * Returns a centered version of any text, isn't guaranteed to be accurate in every environment
     *
     * @param text the text
     * @return the centered text
     */
    @NotNull
    public static String center(@Nullable String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }

        int textSize = getTextWidthInPixels(text);
        int compensation = CENTER_PX - (textSize / 2);
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder builder = new StringBuilder();
        while (compensated < compensation) {
            builder.append(" ");
            compensated += spaceLength;
        }

        return builder + text;
    }

    /**
     * Fills a whole minecraft chat line with a pattern<br>
     * This is not guaranteed to be accurate in every environment
     *
     * @param pattern the pattern
     * @return the line
     */
    @NotNull
    public static String fillLine(@NotNull String pattern) {
        if (StringUtils.isBlank(pattern)) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        int currentSize = 0;
        int patternSize = getTextWidthInPixels(pattern);
        StringBuilder builder = new StringBuilder();

        while (currentSize < (CENTER_PX * 2)) {
            builder.append(pattern);
            currentSize += patternSize;
        }

        return builder.toString();
    }

    /**
     * Returns a list with every string preceded by a color
     *
     * @param color   the color
     * @param strings the list of strings
     * @return the prefixed list of strings
     */
    @NotNull
    public static List<String> prefix(ChatColor color, List<String> strings) {
        return TextFormatting.prefix(color.toString(), strings);
    }

    /**
     * Returns a list with every string preceded by a color
     *
     * @param color   the color
     * @param strings the list of strings
     * @return the prefixed list of strings
     */
    @NotNull
    public static List<String> prefix(ChatColor color, String... strings) {
        return TextFormatting.prefix(color.toString(), strings);
    }

    /**
     * Returns a list with colorized strings using the default {@link ColorFormatting#EXTERNAL_COLOR_CODE}
     *
     * @param strings the list of strings
     * @return the list of colorized strings
     */
    @NotNull
    public static List<String> colorize(List<String> strings) {
        return strings
                .stream()
                .map(ColorFormatting::colorize)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list with colorized strings using the default {@link ColorFormatting#EXTERNAL_COLOR_CODE}
     *
     * @param strings the list of strings
     * @return the list of colorized strings
     */
    @NotNull
    public static List<String> colorize(String... strings) {
        return colorize(Arrays.asList(strings));
    }

    /**
     * Returns a list containing centered versions of the input list's strings<br>
     * This isn't guaranteed to be accurate in every environment
     *
     * @param strings the list of strings
     * @return the list of centered strings
     */
    @NotNull
    public static List<String> center(List<String> strings) {
        return strings
                .stream()
                .map(ColorFormatting::center)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list containing centered versions of the input list's strings<br>
     * This isn't guaranteed to be accurate in every environment
     *
     * @param strings the list of strings
     * @return the list of centered strings
     */
    @NotNull
    public static List<String> center(String... strings) {
        return center(Arrays.asList(strings));
    }

    /**
     * Returns an approximate width in pixels for a given string<br>
     * Keeps track of colors and formatting rules
     *
     * @param text the text
     * @return the width in pixels
     */
    private static int getTextWidthInPixels(@Nullable String text) {
        if (StringUtils.isBlank(text)) {
            return 0;
        }

        int width = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char character : text.toCharArray()) {
            if (character == INTERNAL_COLOR_CODE) {
                previousCode = true;
                continue;
            }

            if (previousCode) {
                previousCode = false;
                isBold = Character.toLowerCase(character) == 'l';

                continue;
            }

            DefaultFontInfo info = DefaultFontInfo.fromCharacter(character);
            width += isBold ? info.getBoldLength() : info.getLength();
            width++;
        }

        return width;
    }

    private static boolean isColorCharacter(char character) {
        return character == INTERNAL_COLOR_CODE || character == EXTERNAL_COLOR_CODE;
    }

}
