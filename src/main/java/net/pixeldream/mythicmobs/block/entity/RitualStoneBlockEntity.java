//package net.pixeldream.mythicmobs.block.entity;
//
//import net.minecraft.block.BlockState;
//import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.util.math.BlockPos;
//import net.pixeldream.mythicmobs.registry.BlockEntityRegistry;
//import software.bernie.geckolib3.core.IAnimatable;
//import software.bernie.geckolib3.core.PlayState;
//import software.bernie.geckolib3.core.builder.AnimationBuilder;
//import software.bernie.geckolib3.core.controller.AnimationController;
//import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
//import software.bernie.geckolib3.core.manager.AnimationData;
//import software.bernie.geckolib3.core.manager.AnimationFactory;
//import software.bernie.geckolib3.util.GeckoLibUtil;
//
//public class RitualStoneBlockEntity extends BlockEntity implements IAnimatable {
//    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", true);
//    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
//
//    public RitualStoneBlockEntity(BlockPos pos, BlockState state) {
//        super(BlockEntityRegistry.RITUAL_STONE_BLOCK_ENTITY, pos, state);
//    }
//
//    @Override
//    public void registerControllers(AnimationData animationData) {
//        animationData.addAnimationController(new AnimationController<RitualStoneBlockEntity>(this, "controller", 0, this::predicate));
//    }
//
//    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        event.getController().setAnimation(IDLE);
//
//        return PlayState.CONTINUE;
//    }
//
//    @Override
//    public AnimationFactory getFactory() {
//        return factory;
//    }
//}
