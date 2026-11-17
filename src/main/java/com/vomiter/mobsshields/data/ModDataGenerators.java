package com.vomiter.mobsshields.data;

import net.minecraftforge.data.event.GatherDataEvent;

public final class ModDataGenerators {
    private ModDataGenerators() {}

    public static void gather(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        generator.addProvider(event.includeServer(), new ShieldMobEquipment(output));
        generator.addProvider(event.includeClient(), new ShieldItemModels(output, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ShieldLanguage(output, "en_us"));
        generator.addProvider(event.includeClient(), new ShieldLanguage(output, "zh_tw"));
        generator.addProvider(event.includeServer(), new ShieldRecipes(output));
        generator.addProvider(event.includeServer(), new ShieldItemTags(output, event.getLookupProvider(), event.getExistingFileHelper()));
    }
}
