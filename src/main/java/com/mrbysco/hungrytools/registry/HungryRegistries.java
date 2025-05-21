package com.mrbysco.hungrytools.registry;

import com.mrbysco.hungrytools.api.SnackMenu;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class HungryRegistries {
	public static void onNewRegistry(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(SnackMenu.REGISTRY_KEY,
				SnackMenu.DIRECT_CODEC, SnackMenu.DIRECT_CODEC);
	}
}
