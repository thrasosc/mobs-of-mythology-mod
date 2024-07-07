package net.pixeldreamstudios.mobs_of_mythology.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SporelingVariant {
    RED(0),

    BROWN(1);

    private static final SporelingVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SporelingVariant::getId)).toArray(SporelingVariant[]::new);
    private final int id;

    SporelingVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static SporelingVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
