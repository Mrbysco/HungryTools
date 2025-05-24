package com.mrbysco.hungrytools.compat.jei;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.api.SnackMenu;
import com.mrbysco.hungrytools.util.MenuUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@JeiPlugin
public class HungryJEICompat implements IModPlugin {
	public static final ResourceLocation PLUGIN_UID = HungryTools.modLoc("main");

	public static final RecipeType<SnackMenu> SNACK_MENU_TYPE = RecipeType.create(HungryTools.MOD_ID, "snack_menu", SnackMenu.class);

	@Nullable
	private IRecipeCategory<SnackMenu> snackCategory;

	@Override
	@NotNull
	public ResourceLocation getPluginUid() {
		return PLUGIN_UID;
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {

	}

	@Override
	public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
		IJeiHelpers jeiHelpers = registration.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registration.addRecipeCategories(
				snackCategory = new SnackCategory(guiHelper)
		);
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registration) {
		var menus = MenuUtil.getSnackMenus().values().stream().toList();
		registration.addRecipes(SNACK_MENU_TYPE, menus);
	}
}
