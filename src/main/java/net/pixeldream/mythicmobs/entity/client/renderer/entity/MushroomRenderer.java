package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import com.google.common.collect.Maps;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.client.model.entity.MushroomModel;
import net.pixeldream.mythicmobs.entity.mobs.MushroomEntity;
import net.pixeldream.mythicmobs.entity.mobs.MushroomVariant;

import java.util.Map;

public class MushroomRenderer extends GeoEntityRenderer<MushroomEntity> {
    public static final Map<MushroomVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MushroomVariant.class), (map) -> {
                map.put(MushroomVariant.RED,
                        new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/mushroom/mushroom_red.png"));
                map.put(MushroomVariant.BROWN,
                        new ResourceLocation(MythicMobs.MOD_ID, "textures/entity/mushroom/mushroom_brown.png"));
            });

    public MushroomRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MushroomModel());
        this.shadowRadius = 0.32f;
    }

    @Override
    public ResourceLocation getTextureLocation(MushroomEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}