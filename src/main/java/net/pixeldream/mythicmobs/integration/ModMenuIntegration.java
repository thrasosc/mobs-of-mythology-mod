package net.pixeldream.mythicmobs.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.pixeldream.mythicmobs.MythicMobs;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> MidnightConfig.getScreen(screen, MythicMobs.MOD_ID);
    }
}