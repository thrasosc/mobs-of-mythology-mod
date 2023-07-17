package net.pixeldream.mythicmobs.entity.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.pixeldream.mythicmobs.MythicMobs;
import net.pixeldream.mythicmobs.entity.WendigoEntity;
import net.pixeldream.mythicmobs.entity.client.model.entity.WendigoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WendigoRenderer extends GeoEntityRenderer<WendigoEntity> {
    public WendigoRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WendigoModel());
        this.shadowRadius = 1.5f;
    }

    @Override
    public Identifier getTextureLocation(WendigoEntity animatable) {
        return new Identifier(MythicMobs.MOD_ID, "textures/entity/wendigo.png");
    }
}
