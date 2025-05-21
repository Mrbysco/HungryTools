package com.mrbysco.hungrytools.api.datagen;

import com.google.common.collect.ImmutableList;
import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.api.SnackMenu;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A base class for generating snack menus.
 */
public abstract class SnackMenuProvider implements DataProvider {
	private final PackOutput output;
	private final CompletableFuture<Provider> registries;
	private final String modid;
	private final Map<String, WithConditions<SnackMenu>> toSerializeMenu = new HashMap<>();

	public SnackMenuProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, String modid) {
		this.output = packOutput;
		this.registries = registries;
		this.modid = modid;
	}

	@Override
	public final CompletableFuture<?> run(CachedOutput cache) {
		return this.registries.thenCompose(registries -> this.run(cache, registries));
	}

	public CompletableFuture<?> run(CachedOutput cache, HolderLookup.Provider registries) {
		start();

		ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();

		Path snackMenuFolder = this.output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(this.modid).resolve(HungryTools.MOD_ID).resolve("snack_menu");
		for (var entry : toSerializeMenu.entrySet()) {
			var name = entry.getKey();
			var modifier = entry.getValue();
			Path modifierPath = snackMenuFolder.resolve(name + ".json");
			futuresBuilder.add(DataProvider.saveStable(cache, registries, SnackMenu.CONDITIONAL_CODEC, Optional.of(modifier), modifierPath));
		}

		return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
	}

	protected abstract void start();

	public void addSnackMenu(String menuID, SnackMenu instance, List<ICondition> conditions) {
		this.toSerializeMenu.put(menuID, new WithConditions<>(conditions, instance));
	}

	public void addSnackMenu(String menuID, SnackMenu instance, ICondition... conditions) {
		addSnackMenu(menuID, instance, Arrays.asList(conditions));
	}

	@Override
	public String getName() {
		return "Snack Menu: " + modid;
	}
}
