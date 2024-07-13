package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.google.common.collect.Maps;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.animal.SporelingEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.model.SporelingModel;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.SporelingVariant;

import java.util.Map;

public class SporelingRenderer extends GeoEntityRenderer<SporelingEntity> {
    public static final Map<SporelingVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SporelingVariant.class), (map) -> {
                map.put(SporelingVariant.RED,
                        ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/sporeling/sporeling_red.png"));
                map.put(SporelingVariant.BROWN,
                        ResourceLocation.fromNamespaceAndPath(MobsOfMythology.MOD_ID, "textures/entity/sporeling/sporeling_brown.png"));
            });

    public SporelingRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SporelingModel());
        this.shadowRadius = 0.32f;
    }

    @Override
    public ResourceLocation getTextureLocation(SporelingEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}