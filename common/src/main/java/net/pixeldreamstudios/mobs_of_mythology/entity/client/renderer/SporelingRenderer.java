package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.google.common.collect.Maps;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.SporelingAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.KoboldWarriorEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.SporelingEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.SporelingVariant;

import java.util.Map;

public class SporelingRenderer extends AzEntityRenderer<SporelingEntity> {

    public static final Map<SporelingVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SporelingVariant.class), (map) -> {
                map.put(SporelingVariant.RED,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/sporeling/sporeling_red.png"));
                map.put(SporelingVariant.BROWN,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/sporeling/sporeling_brown.png"));
            });

    private static final ResourceLocation MODEL =
            MobsOfMythology.modResource("geo/entity/sporeling.geo.json");

    public SporelingRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<SporelingEntity>builder(
                                entity -> MODEL,
                                entity -> LOCATION_BY_VARIANT.get(entity.getVariant())
                        )
                        .setAnimatorProvider(SporelingAnimator::new)
                        .setShadowRadius(0.32F)
                        .build(),
                context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(SporelingEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
