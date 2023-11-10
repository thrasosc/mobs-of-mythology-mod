//package net.pixeldream.mythicmobs.entity.client.renderer.entity;
//
//import mod.azure.azurelib.renderer.GeoEntityRenderer;
//import net.minecraft.client.render.entity.EntityRendererFactory;
//import net.minecraft.util.Identifier;
//import net.pixeldream.mythicmobs.MythicMobs;
//import net.pixeldream.mythicmobs.entity.mobs.BasiliskEntity;
//import net.pixeldream.mythicmobs.entity.client.model.entity.BasiliskModel;
//
//public class BasiliskRenderer extends GeoEntityRenderer<BasiliskEntity> {
//    public BasiliskRenderer(EntityRendererFactory.Context ctx) {
//        super(ctx, new BasiliskModel());
//        this.shadowRadius = 0.85f;
//    }
//
//    @Override
//    public Identifier getTextureLocation(BasiliskEntity animatable) {
//        return new Identifier(MythicMobs.MOD_ID, "textures/entity/basilisk.png");
//    }
//}