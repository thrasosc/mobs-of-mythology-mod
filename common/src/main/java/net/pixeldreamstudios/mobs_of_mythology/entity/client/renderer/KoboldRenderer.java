package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.google.common.collect.Maps;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.KoboldAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.layor.KoboldItemAndHeadLayer;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.KoboldEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.KoboldVariant;

import java.util.Map;

public class KoboldRenderer extends AzEntityRenderer<KoboldEntity> {

    private static final ResourceLocation MODEL = MobsOfMythology.modResource(
            "geo/entity/kobold.geo.json"
    );

    public static final Map<KoboldVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(KoboldVariant.class), (map) -> {
                map.put(KoboldVariant.KOBOLD,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/kobold/kobold.png"));
                map.put(KoboldVariant.KOBOLD_CLOTHED,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/kobold/kobold_cloth.png"));
            });

    private static final ResourceLocation DEFAULT_TEXTURE =
            new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/kobold/kobold.png");

    public KoboldRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<KoboldEntity>builder(MODEL, DEFAULT_TEXTURE)
                        .setAnimatorProvider(KoboldAnimator::new)
                        .setShadowRadius(0.4F)
                        .addRenderLayer(new KoboldItemAndHeadLayer())
                        .build(),
                context
        );

    }

    @Override
    public ResourceLocation getTextureLocation(KoboldEntity entity) {
        return LOCATION_BY_VARIANT.getOrDefault(entity.getVariant(), DEFAULT_TEXTURE);
    }
}
