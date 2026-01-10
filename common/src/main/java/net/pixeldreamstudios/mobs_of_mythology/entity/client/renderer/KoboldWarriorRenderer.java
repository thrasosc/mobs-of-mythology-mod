package net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer;

import com.google.common.collect.Maps;
import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import net.minecraft.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.pixeldreamstudios.mobs_of_mythology.MobsOfMythology;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.animator.KoboldWarriorAnimator;
import net.pixeldreamstudios.mobs_of_mythology.entity.client.renderer.layor.KoboldWarriorItemLayer;
import net.pixeldreamstudios.mobs_of_mythology.entity.mobs.KoboldWarriorEntity;
import net.pixeldreamstudios.mobs_of_mythology.entity.variant.KoboldWarriorVariant;

import java.util.Map;

public class KoboldWarriorRenderer extends AzEntityRenderer<KoboldWarriorEntity> {

    public static final Map<KoboldWarriorVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(KoboldWarriorVariant.class), map -> {
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_1,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_1.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_2,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_2.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_3,
                        new ResourceLocation(MobsOfMythology.MOD_ID, "textures/entity/kobold_warrior/kobold_warrior_3.png"));
            });

    private static final ResourceLocation MODEL =
            MobsOfMythology.modResource("geo/entity/kobold_warrior.geo.json");

    public KoboldWarriorRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<KoboldWarriorEntity>builder(
                                entity -> MODEL,
                                entity -> LOCATION_BY_VARIANT.get(entity.getVariant())
                        )
                        .setAnimatorProvider(KoboldWarriorAnimator::new)
                        .setShadowRadius(0.4F)
                        .addRenderLayer(new KoboldWarriorItemLayer())
                        .build(),
                context
        );
    }


    @Override
    public ResourceLocation getTextureLocation(KoboldWarriorEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
