package net.pixeldream.mythicmobs.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum KoboldWarriorVariant {
    KOBOLD_WARRIOR(0),
    KOBOLD_WARRIOR_2(1),
    KOBOLD_WARRIOR_3(2);

    private static final KoboldWarriorVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(KoboldWarriorVariant::getId)).toArray(KoboldWarriorVariant[]::new);
    private final int id;

    KoboldWarriorVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static KoboldWarriorVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
