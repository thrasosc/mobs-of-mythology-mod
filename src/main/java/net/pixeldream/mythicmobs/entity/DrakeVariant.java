package net.pixeldream.mythicmobs.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum DrakeVariant {
    DRAKE_1(0),
    DRAKE_2(1),
    DRAKE_3(2),
    DRAKE_4(3),
    DRAKE_5(4),
    DRAKE_6(5),
    DRAKE_7(6);

    private static final DrakeVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(DrakeVariant::getId)).toArray(DrakeVariant[]::new);
    private final int id;

    DrakeVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static DrakeVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
