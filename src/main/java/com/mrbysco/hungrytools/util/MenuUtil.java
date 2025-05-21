package com.mrbysco.hungrytools.util;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.api.SnackMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for getting SnackMenus from ItemStacks
 */
@EventBusSubscriber
public class MenuUtil {
	private static final Map<ResourceLocation, SnackMenu> snackMenus = new HashMap<>();
	private static final Map<Item, SnackMenu> snackCache = new HashMap<>();


	/**
	 * Check if the given ItemStack is a valid tool
	 *
	 * @param stack the ItemStack to check
	 * @return true if the ItemStack is a valid tool, false otherwise
	 */
	public static boolean isValidTool(@NotNull ItemStack stack) {
		return !stack.isEmpty() && stack.has(DataComponents.TOOL);
	}

	/**
	 * Get the cached SnackMenu for the given ItemStack or compute it if not present
	 *
	 * @param stack the ItemStack to check
	 * @return the SnackMenu if it matches, null otherwise
	 */
	@Nullable
	public static SnackMenu getSnackMenu(@NotNull ItemStack stack) {
		if (!stack.isDamaged()) return null;
		return snackCache.computeIfAbsent(stack.getItem(), item -> getInternalSnack(stack));
	}

	/**
	 * Get the SnackMenu for the given ItemStack from the internal menu map
	 *
	 * @param stack the ItemStack to check
	 * @return the SnackMenu if it matches, null otherwise
	 */
	@Nullable
	private static SnackMenu getInternalSnack(@NotNull ItemStack stack) {
		List<SnackMenu> matchingMenus = snackMenus.values().stream()
				.filter(snackMenu -> snackMenu.matchesTool(stack))
				.toList();
		if (matchingMenus.isEmpty()) {
			return null;
		}
		List<SnackMenu> snackMenuList = Collections.singletonList(matchingMenus.stream()
				.reduce(null, (highest_priority, next) ->
						highest_priority == null || next.priority() > highest_priority.priority()
								? next
								: highest_priority));
		if (!snackMenuList.isEmpty()) {
			return snackMenuList.getFirst();
		}
		return null;
	}

	@SubscribeEvent
	public static void onDatapackSync(OnDatapackSyncEvent event) {
		final RegistryAccess registryAccess = event.getPlayerList().getServer().registryAccess();

		snackMenus.clear();
		snackCache.clear();
		final Registry<SnackMenu> snackMenuRegistry = registryAccess.registryOrThrow(SnackMenu.REGISTRY_KEY);
		snackMenuRegistry.entrySet().forEach((key) -> snackMenus.put(key.getKey().location(), key.getValue()));
		HungryTools.LOGGER.info("Loaded Snack Menus for tools");
	}
}
