package net.pixeldreamstudios.mobs_of_mythology;

import mod.azure.azurelib.common.api.common.config.Config;
import mod.azure.azurelib.common.internal.common.config.Configurable;

@Config(id = MobsOfMythology.MOD_ID)
public class MobsOfMythologyConfig {
    @Configurable
    public double automatonHealth = 100.0;
    @Configurable
    public double automatonAttackDamage = 16.0;

    @Configurable
    public double chupacabraHealth = 16.0;
    @Configurable
    public double chupacabraAttackDamage = 6.0;

    @Configurable
    public double koboldHealth = 20.0;
    @Configurable
    public double koboldAttackDamage = 2.5;

    @Configurable
    public double koboldWarriorHealth = 20.0;
    @Configurable
    public double koboldWarriorArmor = 6.0;
    @Configurable
    public double koboldWarriorAttackDamage = 5.5;

    @Configurable
    public double drakeHealth = 30.0;
    @Configurable
    public double drakeAttackDamage = 5.0;

    @Configurable
    public double mushroomHealth = 6.0;;
    @Configurable
    public static String[] redMushroomLines = {
            "playerGreeting",
            "Seen any smurfs lately?",
            "I hate Gargamel...",
            "Mondays, huh?",
            "You're a big fellow!",
            "Please don't eat me!",
            "Why did the Creeper hang out with me? Because I'm such a fun-gi to be around!",
            "They say I'm a fun-gi, but I think I'm just a cap-tivating conversationalist!",
            "Looking for a spore-tacular time? You've found the right mushroom!",
            "Did you hear about the fungi who threw a party? It was a real spore-gy!",
            "I'm not edibles!",
            "My cousin Brownie is an alchemist.",
            "Do you know of a bard named Smash Mouth? I hear he's an All Star.",
            "I'm not a regular mushroom; I'm a spore-tacular mushroom!",
            "Some people blame me for the apocalypse...",
            "Γειά σου Ελλάδα!"
    };
    @Configurable
    public static String[] brownMushroomLines = {
            "Why do I feel like a fun-guy in a no-fun zone?",
            "You want a piece of my spore attitude? Take a hike!",
            "Life's a spore-t, and then you decompose.",
            "Step back, or I'll give you a spore-tacular scowl!",
            "Why do players always pick on mushrooms? Can't we just have a cap-py existence?",
            "I'd rather be left alone in my dark corner. No mushroom for company!",
            "I don't have mushroom for joy, just a whole lot of fung-titude!",
            "Have a fung-tastic day...",
            "I am responsible for the apocalypse.",
            "I'm not grumpy, I'm just a fungi with a perpetual frown.",
            "Why are you bothering me? Can't you see I'm having a spore day?",
            "Why do players keep trying to cheer me up? Do I look like a sunflower to you?",
            "I'm like a mushroom, growing in the darkness of my grumpy thoughts.",
            "Step lightly, or you might awaken the wrath of the grumpy mushroom!",
            "Why be a fun-guy when you can be a grump-guy?",
            "I've mastered the art of being perpetually annoyed. It's my spore-cialty!",
            "Let's get blazed! Go bring the blaze rod.",
            "Is it 4:20 already?"
    };
}
