package com.mrbysco.hungrytools;

import com.mojang.logging.LogUtils;
import com.mrbysco.hungrytools.client.ClientHandler;
import com.mrbysco.hungrytools.config.HungryConfig;
import com.mrbysco.hungrytools.handler.ToolHandler;
import com.mrbysco.hungrytools.registry.HungryDataComponents;
import com.mrbysco.hungrytools.registry.HungryRegistries;
import com.mrbysco.hungrytools.registry.HungrySounds;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(HungryTools.MOD_ID)
public class HungryTools {
	public static final String MOD_ID = "hungrytools";
	public static final Logger LOGGER = LogUtils.getLogger();

	public HungryTools(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(Type.COMMON, HungryConfig.commonSpec);

		eventBus.addListener(HungryRegistries::onNewRegistry);

		HungryDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
		HungrySounds.SOUND_EVENTS.register(eventBus);

		NeoForge.EVENT_BUS.register(new ToolHandler());

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			NeoForge.EVENT_BUS.addListener(ClientHandler::renderTooltip);
		}
	}

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
