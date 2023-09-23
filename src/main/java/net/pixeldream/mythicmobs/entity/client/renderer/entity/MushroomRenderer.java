package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import com.google.common.collect.Maps;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.MushroomEntity;
import net.pixeldream.mythicmobs.entity.MushroomVariant;
import net.pixeldream.mythicmobs.entity.client.model.entity.MushroomModel;

import java.util.Map;

public class MushroomRenderer extends GeoEntityRenderer<MushroomEntity> {
    public static final Map<MushroomVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MushroomVariant.class), (map) -> {
                map.put(MushroomVariant.RED,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/mushroom/mushroom_red.png"));
                map.put(MushroomVariant.BROWN,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/mushroom/mushroom_brown.png"));
            });

    public MushroomRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MushroomModel());
        this.shadowRadius = 0.32f;
    }

    @Override
    public Identifier getTextureLocation(MushroomEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}