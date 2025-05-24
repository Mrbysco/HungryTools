package com.mrbysco.hungrytools.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.hungrytools.HungryTools;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a snack menu for a tool.
 *
 * @param tool      The tool that can be used to consume the snacks.
 * @param snacks    The list of snacks that can be consumed.
 * @param desirable The list of items that a tool may desire to consume.
 * @param priority  The priority of the snack menu. (Lower values are higher priority)
 */
public record SnackMenu(Ingredient tool, List<SnackEntry> snacks, List<Ingredient> desirable, int priority) {
	public static final ResourceKey<Registry<SnackMenu>> REGISTRY_KEY = ResourceKey.createRegistryKey(
			HungryTools.modLoc("snack_menu"));

	public static Codec<SnackMenu> DIRECT_CODEC = ExtraCodecs.catchDecoderException(
			RecordCodecBuilder.create(
					instance -> instance.group(
							Ingredient.CODEC_NONEMPTY.fieldOf("tool").forGetter(SnackMenu::tool),
							SnackEntry.ENTRY_CODEC.listOf().fieldOf("snacks").forGetter(SnackMenu::snacks),
							Ingredient.CODEC.listOf().fieldOf("desirable").forGetter(SnackMenu::desirable),
							Codec.INT.optionalFieldOf("priority", 10).forGetter(SnackMenu::priority)
					).apply(instance, SnackMenu::new)
			)
	);
	public static final Codec<Optional<WithConditions<SnackMenu>>> CONDITIONAL_CODEC = ConditionalOps.createConditionalCodecWithConditions(DIRECT_CODEC);

	/**
	 * Creates a new SnackMenu with the specified tool, snacks, and desirable items.
	 *
	 * @param tool      The tool that can be used to consume the snacks.
	 * @param snacks    The list of snacks that can be consumed.
	 * @param desirable The list of items that a tool may desire to consume.
	 */
	public SnackMenu(Ingredient tool, List<SnackEntry> snacks, List<Ingredient> desirable) {
		this(tool, snacks, desirable, 10);
	}

	/**
	 * Checks if the tool matches the specified item stack.
	 *
	 * @param stack The item stack to check.
	 * @return True if the tool matches the item stack, false otherwise.
	 */
	public boolean matchesTool(@NotNull ItemStack stack) {
		return !stack.isEmpty() && this.tool.test(stack);
	}

	/**
	 * Gets the food value of the specified item stack.
	 *
	 * @param stack The item stack to check.
	 * @return An Optional containing the food value if the item stack is a snack, or an empty Optional if not.
	 */
	public Optional<Integer> getFoodValue(@NotNull ItemStack stack) {
		if (stack.isEmpty())
			return Optional.empty();
		for (SnackEntry entry : this.snacks) {
			if (entry.ingredient().test(stack)) {
				return Optional.of(entry.hunger);
			}
		}
		return Optional.empty();
	}

	/**
	 * Checks if the specified item stack is a snack.
	 *
	 * @param snack The item stack to check.
	 * @return True if the item stack is a snack, false otherwise.
	 */
	public boolean matchesSnack(@NotNull ItemStack snack) {
		if (snack.isEmpty())
			return false;
		return snacks.stream().anyMatch(entry -> entry.ingredient().test(snack));
	}

	/**
	 * Gets a random snack from the list of snacks.
	 *
	 * @return A random snack item stack.
	 */
	public ItemStack getRandomSnack(RandomSource random) {
		List<ItemStack> allStacks = new ArrayList<>();
		for (SnackEntry entry : this.snacks) {
			for (ItemStack stack : entry.ingredient().getItems()) {
				if (!stack.isEmpty()) {
					allStacks.add(stack);
				}
			}
		}
		if (allStacks.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			return allStacks.get(random.nextInt(allStacks.size())).copy();
		}
	}

	/**
	 * Gets a random desirable item from the list of desirable items.
	 *
	 * @param random The random source to use for generating the random index.
	 * @return A random desirable item stack.
	 */
	public ItemStack getRandomDesirable(RandomSource random) {
		List<ItemStack> allStacks = new ArrayList<>();
		for (Ingredient entry : this.desirable) {
			for (ItemStack stack : entry.getItems()) {
				if (!stack.isEmpty()) {
					allStacks.add(stack);
				}
			}
		}
		if (allStacks.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			return allStacks.get(random.nextInt(allStacks.size())).copy();
		}
	}

	/**
	 * Represents a snack entry in the snack menu.
	 *
	 * @param ingredient The ingredient that can be consumed as a snack.
	 * @param hunger     The amount of hunger restored by consuming the snack.
	 */
	public record SnackEntry(Ingredient ingredient, int hunger) {

		public static Codec<SnackEntry> ENTRY_CODEC = ExtraCodecs.catchDecoderException(
				RecordCodecBuilder.create(
						instance -> instance.group(
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(SnackEntry::ingredient),
								Codec.INT.fieldOf("hunger").forGetter(SnackEntry::hunger)
						).apply(instance, SnackEntry::new)
				)
		);
	}
}
