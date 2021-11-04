package dev.fumaz.commons.bukkit.gui;

// Yes, this is ugly, but who cares?
public final class Slots {

    public static int at(int row, int column) {
        return ((row * 9) + column) - 1;
    }

    public static int middle(int row) {
        return 4 * (row * 9);
    }

    public static int start(int row) {
        return (row - 1) * 9;
    }

    public static int end(int row) {
        return (row * 9) - 1;
    }

    public static int second(int row) {
        return start(row) + 1;
    }

    public static int penultimate(int row) {
        return end(row) - 1;
    }

    public static int leftMiddle(int row) {
        return start(row) + 2;
    }

    public static int rightMiddle(int row) {
        return end(row) - 2;
    }

    public static int lessMiddle(int row) {
        return start(row) + 3;
    }

    public static int moreMiddle(int row) {
        return end(row) - 3;
    }

    public static int first(int row) {
        return start(row);
    }

    public static int third(int row) {
        return start(row) + 2;
    }

    public static int fourth(int row) {
        return start(row) + 3;
    }

    public static int fifth(int row) {
        return start(row) + 4;
    }

    public static int sixth(int row) {
        return start(row) + 5;
    }

    public static int seventh(int row) {
        return start(row) + 6;
    }

    public static int eight(int row) {
        return start(row) + 7;
    }

    public static int nineth(int row) {
        return start(row) + 8;
    }

    public static int last(int row) {
        return nineth(row);
    }

}
