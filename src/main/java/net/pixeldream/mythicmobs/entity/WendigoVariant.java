package net.pixeldream.mythicmobs.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum WendigoVariant {
    WENDIGO(0), WENDIGO_STANDING(1);

    private static final WendigoVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(WendigoVariant::getId)).toArray(WendigoVariant[]::new);
    private final int id;

    WendigoVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static WendigoVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
