package net.pixeldream.mythicmobs.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.KoboldWarriorEntity;
import net.pixeldream.mythicmobs.entity.KoboldWarriorVariant;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class KoboldWarriorRenderer extends GeoEntityRenderer<KoboldWarriorEntity> {
    public static final Map<KoboldWarriorVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(KoboldWarriorVariant.class), (map) -> {
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_warrior.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_2,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_warrior_2.png"));
                map.put(KoboldWarriorVariant.KOBOLD_WARRIOR_3,
                        new Identifier(MythicMobs.MOD_ID, "textures/entity/kobold_warrior_3.png"));
            });
    public KoboldWarriorRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KoboldWarriorModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureResource(KoboldWarriorEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderLayer getRenderType(KoboldWarriorEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
