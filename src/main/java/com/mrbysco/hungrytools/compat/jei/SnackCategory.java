package com.mrbysco.hungrytools.compat.jei;

import com.mrbysco.hungrytools.api.SnackMenu;
import com.mrbysco.hungrytools.api.SnackMenu.SnackEntry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class SnackCategory implements IRecipeCategory<SnackMenu> {

	private final IDrawable background;
	private final IDrawable slot;
	private final IDrawable icon;
	private final Component title;

	public SnackCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createBlankDrawable(64, 20);
		this.slot = guiHelper.getSlotDrawable();
		this.title = Component.literal("Hungry Tools");
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.IRON_PICKAXE));
	}

	@Override
	public RecipeType<SnackMenu> getRecipeType() {
		return HungryJEICompat.SNACK_MENU_TYPE;
	}

	@Override
	public Component getTitle() {
		return title;
	}

	@SuppressWarnings("removal")
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, SnackMenu recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.CATALYST, 0, 4).addIngredients(recipe.tool());
		List<Ingredient> snacks = recipe.snacks().stream().map(SnackEntry::ingredient).toList();
		List<ItemStack> snackStacks = new ArrayList<>();
		snacks.forEach(ingredient -> snackStacks.addAll(List.of(ingredient.getItems())));
		builder.addSlot(RecipeIngredientRole.INPUT, 24, 4).addIngredients(VanillaTypes.ITEM_STACK, snackStacks)
				.addRichTooltipCallback(new HungryTooltip("hungrytools.tooltip.snack"));
		List<ItemStack> desirableStacks = new ArrayList<>();
		recipe.desirable().forEach(ingredient -> desirableStacks.addAll(List.of(ingredient.getItems())));
		builder.addSlot(RecipeIngredientRole.INPUT, 48, 4).addIngredients(VanillaTypes.ITEM_STACK, desirableStacks)
				.addRichTooltipCallback(new HungryTooltip("hungrytools.tooltip.desirable"));


	}

	@Override
	public void draw(SnackMenu recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		this.slot.draw(guiGraphics, -1, 3);
		this.slot.draw(guiGraphics, 23, 3);
		this.slot.draw(guiGraphics, 47, 3);
	}

	public static class HungryTooltip implements IRecipeSlotRichTooltipCallback {
		private final String tooltipText;

		public HungryTooltip(String tooltipText) {
			this.tooltipText = tooltipText;
		}

		@Override
		public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {
			tooltip.add(Component.translatable(tooltipText).withStyle(ChatFormatting.GOLD));
		}
	}
}
