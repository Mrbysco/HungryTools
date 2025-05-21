package com.mrbysco.hungrytools.datagen;

import com.mrbysco.hungrytools.datagen.client.HungryLanguageProvider;
import com.mrbysco.hungrytools.datagen.client.HungrySoundProvider;
import com.mrbysco.hungrytools.datagen.server.HungryBlockTagsProvider;
import com.mrbysco.hungrytools.datagen.server.HungryItemTagsProvider;
import com.mrbysco.hungrytools.datagen.server.HungryMenuProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class HungryDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(true, new HungryMenuProvider(packOutput, lookupProvider));

			HungryBlockTagsProvider blockTags = new HungryBlockTagsProvider(packOutput, lookupProvider, helper);
			generator.addProvider(true, blockTags);
			generator.addProvider(true, new HungryItemTagsProvider(packOutput, lookupProvider, blockTags, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(true, new HungryLanguageProvider(packOutput));
			generator.addProvider(true, new HungrySoundProvider(packOutput, helper));
		}
	}
}
