package net.pixeldream.mythicmobs.entity.mobs;

import java.util.Arrays;
import java.util.Comparator;

public enum KoboldVariant {
    KOBOLD(0), KOBOLD_CLOTHED(1);

    private static final KoboldVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(KoboldVariant::getId)).toArray(KoboldVariant[]::new);
    private final int id;

    KoboldVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static KoboldVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
