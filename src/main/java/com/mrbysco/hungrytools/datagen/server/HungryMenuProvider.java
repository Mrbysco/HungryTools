package com.mrbysco.hungrytools.datagen.server;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.api.datagen.SnackMenuBuilder;
import com.mrbysco.hungrytools.api.datagen.SnackMenuProvider;
import com.mrbysco.hungrytools.registry.HungryTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class HungryMenuProvider extends SnackMenuProvider {
	public HungryMenuProvider(PackOutput packOutput, CompletableFuture<Provider> registries) {
		super(packOutput, registries, HungryTools.MOD_ID);
	}

	@Override
	protected void start() {
		addSnackMenu("pickaxe",
				new SnackMenuBuilder(Ingredient.of(ItemTags.PICKAXES))
						.addSnack(Ingredient.of(HungryTags.Items.PICKAXE_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.PICKAXE_DESIRABLES))
						.build()
		);
		addSnackMenu("shovel",
				new SnackMenuBuilder(Ingredient.of(ItemTags.SHOVELS))
						.addSnack(Ingredient.of(HungryTags.Items.SHOVEL_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.SWORD_DESIRABLES))
						.build()
		);
		addSnackMenu("axe",
				new SnackMenuBuilder(Ingredient.of(ItemTags.AXES))
						.addSnack(Ingredient.of(HungryTags.Items.AXE_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.AXE_DESIRABLES))
						.build()
		);
		addSnackMenu("hoe",
				new SnackMenuBuilder(Ingredient.of(ItemTags.HOES))
						.addSnack(Ingredient.of(HungryTags.Items.HOE_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.HOE_DESIRABLES))
						.build()
		);
		addSnackMenu("sword",
				new SnackMenuBuilder(Ingredient.of(ItemTags.SWORDS))
						.addSnack(Ingredient.of(HungryTags.Items.SWORD_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.SWORD_DESIRABLES))
						.build()
		);
		addSnackMenu("shear",
				new SnackMenuBuilder(Ingredient.of(Tags.Items.TOOLS_SHEAR))
						.addSnack(Ingredient.of(HungryTags.Items.SHEARS_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.SHEARS_DESIRABLES))
						.build()
		);
		addSnackMenu("igniter",
				new SnackMenuBuilder(Ingredient.of(Tags.Items.TOOLS_IGNITER))
						.addSnack(Ingredient.of(HungryTags.Items.FLINT_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.FLINT_DESIRABLES))
						.build()
		);
		addSnackMenu("fishing_rod",
				new SnackMenuBuilder(Ingredient.of(Tags.Items.TOOLS_FISHING_ROD))
						.addSnack(Ingredient.of(HungryTags.Items.FISHING_ROD_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.FISHING_ROD_DESIRABLES))
						.build()
		);
		addSnackMenu("bow",
				new SnackMenuBuilder(Ingredient.of(Tags.Items.TOOLS_BOW))
						.addSnack(Ingredient.of(HungryTags.Items.BOW_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.BOW_DESIRABLES))
						.build()
		);
		addSnackMenu("crossbow",
				new SnackMenuBuilder(Ingredient.of(Tags.Items.TOOLS_CROSSBOW))
						.addSnack(Ingredient.of(HungryTags.Items.CROSSBOW_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.CROSSBOW_DESIRABLES))
						.build()
		);
		addSnackMenu("trident",
				new SnackMenuBuilder(Ingredient.of(Items.TRIDENT))
						.addSnack(Ingredient.of(HungryTags.Items.TRIDENT_SNACKS), 2)
						.addSnack(Ingredient.of(Items.WET_SPONGE), Integer.MAX_VALUE) // Wet Sponge is a special case
						.addDesirable(Ingredient.of(HungryTags.Items.TRIDENT_DESIRABLES))
						.build()
		);
		addSnackMenu("mace",
				new SnackMenuBuilder(Ingredient.of(Tags.Items.TOOLS_MACE))
						.addSnack(Ingredient.of(HungryTags.Items.MACE_SNACKS), 2)
						.addDesirable(Ingredient.of(HungryTags.Items.MACE_DESIRABLES))
						.build()
		);

	}
}
