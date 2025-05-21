package com.mrbysco.hungrytools.registry;

import com.mrbysco.hungrytools.HungryTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class HungryTags {
	public class Items {
		public static final TagKey<Item> PICKAXE_SNACKS = create("pickaxe_snacks");
		public static final TagKey<Item> SHOVEL_SNACKS = create("shovel_snacks");
		public static final TagKey<Item> AXE_SNACKS = create("axe_snacks");
		public static final TagKey<Item> HOE_SNACKS = create("hoe_snacks");
		public static final TagKey<Item> SWORD_SNACKS = create("sword_snacks");
		public static final TagKey<Item> SHEARS_SNACKS = create("shears_snacks");
		public static final TagKey<Item> FLINT_SNACKS = create("flint_snacks");
		public static final TagKey<Item> FISHING_ROD_SNACKS = create("fishing_rod_snacks");
		public static final TagKey<Item> BOW_SNACKS = create("bow_snacks");
		public static final TagKey<Item> CROSSBOW_SNACKS = create("crossbow_snacks");
		public static final TagKey<Item> TRIDENT_SNACKS = create("trident_snacks");
		public static final TagKey<Item> MACE_SNACKS = create("mace_snacks");

		public static final TagKey<Item> PICKAXE_DESIRABLES = create("pickaxe_desirables");
		public static final TagKey<Item> SHOVEL_DESIRABLES = create("shovel_desirables");
		public static final TagKey<Item> AXE_DESIRABLES = create("axe_desirables");
		public static final TagKey<Item> HOE_DESIRABLES = create("hoe_desirables");
		public static final TagKey<Item> SWORD_DESIRABLES = create("sword_desirables");
		public static final TagKey<Item> SHEARS_DESIRABLES = create("shears_desirables");
		public static final TagKey<Item> FLINT_DESIRABLES = create("flint_desirables");
		public static final TagKey<Item> FISHING_ROD_DESIRABLES = create("fishing_rod_desirables");
		public static final TagKey<Item> BOW_DESIRABLES = create("bow_desirables");
		public static final TagKey<Item> CROSSBOW_DESIRABLES = create("crossbow_desirables");
		public static final TagKey<Item> TRIDENT_DESIRABLES = create("trident_desirables");
		public static final TagKey<Item> MACE_DESIRABLES = create("mace_desirables");

		private static TagKey<Item> create(String name) {
			return TagKey.create(Registries.ITEM, HungryTools.modLoc(name));
		}
	}
}
