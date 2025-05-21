package com.mrbysco.hungrytools.api.datagen;

import com.mrbysco.hungrytools.api.SnackMenu;
import com.mrbysco.hungrytools.api.SnackMenu.SnackEntry;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for creating SnackMenu instances.
 */
public class SnackMenuBuilder {
	private final Ingredient tool;
	private final List<SnackEntry> snacks;
	private final List<Ingredient> desirable;
	private int priority = 10;

	/**
	 * Creates a new SnackMenuBuilder with the specified tool.
	 *
	 * @param tool The tool that can be used to consume the snacks.
	 */
	public SnackMenuBuilder(Ingredient tool) {
		this.tool = tool;
		this.snacks = new ArrayList<>();
		this.desirable = new ArrayList<>();
	}

	/**
	 * Adds a snack to the SnackMenu.
	 *
	 * @param snack The snack entry to add.
	 * @return The SnackMenuBuilder instance for method chaining.
	 */
	public SnackMenuBuilder addSnack(Ingredient snack, int hunger) {
		this.snacks.add(new SnackEntry(snack, hunger));
		return this;
	}

	/**
	 * Adds a snack to the SnackMenu with the specified ingredient and hunger value.
	 *
	 * @param ingredient The ingredient that can be consumed as a snack.
	 * @return The SnackMenuBuilder instance for method chaining.
	 */
	public SnackMenuBuilder addDesirable(Ingredient ingredient) {
		this.desirable.add(ingredient);
		return this;
	}

	/**
	 * Sets the priority of the SnackMenu.
	 *
	 * @param priority The priority of the snack menu. (Lower values are higher priority)
	 * @return The SnackMenuBuilder instance for method chaining.
	 */
	public SnackMenuBuilder setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	/**
	 * Builds the SnackMenu with the specified tool, snacks, and desirable items.
	 *
	 * @return The built SnackMenu.
	 */
	public SnackMenu build() {
		return new SnackMenu(this.tool, this.snacks, this.desirable, this.priority);
	}
}
