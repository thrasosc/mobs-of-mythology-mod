package net.pixeldream.mythicmobs.entity.mobs;

import java.util.Arrays;
import java.util.Comparator;

public enum MushroomVariant {
    RED(0),

    BROWN(1);

    private static final MushroomVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomVariant::getId)).toArray(MushroomVariant[]::new);
    private final int id;

    MushroomVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static MushroomVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
