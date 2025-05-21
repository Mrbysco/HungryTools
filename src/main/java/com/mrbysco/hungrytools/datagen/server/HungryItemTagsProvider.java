package com.mrbysco.hungrytools.datagen.server;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.registry.HungryTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class HungryItemTagsProvider extends ItemTagsProvider {
	public HungryItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
	                              TagsProvider<Block> blockTagProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blockTagProvider.contentsGetter(), HungryTools.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		this.tag(HungryTags.Items.PICKAXE_SNACKS).addTags(
				Tags.Items.COBBLESTONES, Tags.Items.STONES, Tags.Items.END_STONES,
				Tags.Items.BRICKS, ItemTags.WALLS, Tags.Items.CONCRETES,
				Tags.Items.GLASS_BLOCKS, Tags.Items.GLASS_PANES
		);
		this.tag(HungryTags.Items.SHOVEL_SNACKS).addTags(
				ItemTags.DIRT, Tags.Items.GRAVELS, ItemTags.SAND,
				Tags.Items.CONCRETE_POWDERS
		).add(Items.CLAY);
		this.tag(HungryTags.Items.AXE_SNACKS).addTags(
				Tags.Items.BARRELS_WOODEN, Tags.Items.CHESTS_WOODEN, Tags.Items.BOOKSHELVES,
				Tags.Items.FENCE_GATES_WOODEN, Tags.Items.FENCES_WOODEN
		);
		this.tag(HungryTags.Items.HOE_SNACKS).addTags(
				Tags.Items.CROPS
		).add(Items.BONE_MEAL);
		this.tag(HungryTags.Items.SWORD_SNACKS).addTags(
				Tags.Items.FOODS_RAW_MEAT, Tags.Items.STRINGS, Tags.Items.BONES,
				Tags.Items.LEATHERS
		).add(
				Items.VINE, Items.COBWEB, Items.COCOA_BEANS,
				Items.BAMBOO, Items.BIG_DRIPLEAF, Items.CHORUS_FLOWER,
				Items.CHORUS_FRUIT, Items.CHORUS_PLANT, Items.MELON,
				Items.MELON_SLICE, Items.PUMPKIN, Items.CARVED_PUMPKIN,
				Items.JACK_O_LANTERN, Items.GLOW_LICHEN, Items.MOSS_CARPET
		);
		this.tag(HungryTags.Items.SHEARS_SNACKS).addTags(
				ItemTags.WOOL, ItemTags.WOOL_CARPETS, ItemTags.LEAVES
		).add(
				Items.GLOW_LICHEN, Items.VINE, Items.COBWEB,
				Items.TALL_GRASS, Items.LARGE_FERN, Items.DEAD_BUSH,
				Items.FERN, Items.SEAGRASS
		);
		this.tag(HungryTags.Items.FLINT_SNACKS).addTags(
				Tags.Items.GRAVELS
		).add(
				Items.FLINT, Items.IRON_INGOT, Items.FIRE_CHARGE
		);
		this.tag(HungryTags.Items.FISHING_ROD_SNACKS).addTags(
				ItemTags.FISHES
		).add(
				Items.STICK, Items.ENCHANTED_BOOK, Items.TRIPWIRE_HOOK,
				Items.BAMBOO, Items.LEATHER_BOOTS, Items.INK_SAC,
				Items.ROTTEN_FLESH, Items.LEATHER, Items.BOOK
		);
		this.tag(HungryTags.Items.BOW_SNACKS).addTags(
				ItemTags.ARROWS, Tags.Items.STRINGS, Tags.Items.RODS_WOODEN
		);
		this.tag(HungryTags.Items.CROSSBOW_SNACKS).addTags(
				ItemTags.ARROWS, Tags.Items.STRINGS, Tags.Items.RODS_WOODEN
		);
		this.tag(HungryTags.Items.TRIDENT_SNACKS).addTags(
				ItemTags.FISHES
		).add(
				Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS, Items.COPPER_INGOT,
				Items.ROTTEN_FLESH
		); //Items.WET_SPONGE
		this.tag(HungryTags.Items.MACE_SNACKS).add(
				Items.TRIAL_KEY, Items.OMINOUS_TRIAL_KEY, Items.BREEZE_ROD,
				Items.ARROW, Items.WATER_BUCKET, Items.SNOWBALL,
				Items.EGG, Items.FIRE_CHARGE, Items.IRON_INGOT,
				Items.GOLD_INGOT, Items.DIAMOND, Items.NETHERITE_INGOT,
				Items.MUSIC_DISC_CREATOR_MUSIC_BOX, Items.MUSIC_DISC_CREATOR
		);

	}
}
