package com.mrbysco.hungrytools.client;

import com.mojang.datafixers.util.Either;
import com.mrbysco.hungrytools.registry.HungryDataComponents;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

public class ClientHandler {

	public static void renderTooltip(RenderTooltipEvent.GatherComponents event) {
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty() && stack.has(HungryDataComponents.TOOL_CONTENTS)) {
			BundleContents contents = stack.getOrDefault(HungryDataComponents.TOOL_CONTENTS, BundleContents.EMPTY);
			event.getTooltipElements().add(Either.right(new BundleTooltip(contents)));
		}
	}
}
