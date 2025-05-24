package com.mrbysco.hungrytools.handler;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.api.SnackMenu;
import com.mrbysco.hungrytools.config.HungryConfig;
import com.mrbysco.hungrytools.registry.HungryDataComponents;
import com.mrbysco.hungrytools.registry.HungrySounds;
import com.mrbysco.hungrytools.util.MenuUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ToolHandler {

	@SubscribeEvent
	public void onStack(ItemStackedOnOtherEvent event) {
		final Player player = event.getPlayer();
		final ItemStack stack = event.getStackedOnItem();
		if (MenuUtil.isValidTool(stack) && stack.isDamaged()) {
			SnackMenu menu = MenuUtil.getSnackMenu(stack);
			if (menu == null) {
				return;
			}

			// Do something with the tool
			BundleContents contents = stack.getOrDefault(HungryDataComponents.TOOL_CONTENTS, BundleContents.EMPTY);
			if (event.getClickAction() == ClickAction.SECONDARY) {
				ItemStack other = event.getCarriedItem();
				boolean matches = false;
				Ingredient desiredItem = stack.get(HungryDataComponents.DESIRED);
				if (desiredItem != null) {
					if (desiredItem.test(other)) {
						matches = true;
					} else {
						player.sendSystemMessage(Component.translatable("hungrytools.insertion.desired_fail").withStyle(ChatFormatting.RED));
						return;
					}
				}

				if (!matches && !menu.matchesSnack(other)) {
					player.sendSystemMessage(Component.translatable("hungrytools.insertion.fail").withStyle(ChatFormatting.RED));
					return;
				}

				BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(contents);
				if (other.isEmpty()) {
					ItemStack itemstack = bundlecontents$mutable.removeOne();
					if (itemstack != null) {
						player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 1F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
						event.getCarriedSlotAccess().set(itemstack);
					}
				} else {
					int i = bundlecontents$mutable.tryInsert(other);
					if (i > 0) {
						player.playSound(SoundEvents.BUNDLE_INSERT, 1F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
					}
				}

				stack.set(HungryDataComponents.TOOL_CONTENTS, bundlecontents$mutable.toImmutable());
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void inventoryTick(PlayerTickEvent.Pre event) {
		final Player player = event.getEntity();
		final Level level = player.level();
		final long gameTime = level.getGameTime();
		final float tickRate = level.tickRateManager().tickrate();
		if (HungryConfig.COMMON.stomachRumbling.getAsBoolean() && !level.isClientSide && gameTime % tickRate == 0) {
			double rumbleChance = HungryConfig.COMMON.rumbleChance.getAsDouble();
			for (InteractionHand hand : InteractionHand.values()) {
				final ItemStack stack = player.getItemInHand(hand);
				if (MenuUtil.isValidTool(stack) && stack.isDamaged()) {
					BundleContents contents = stack.getOrDefault(HungryDataComponents.TOOL_CONTENTS, BundleContents.EMPTY);
					if (contents.isEmpty() && stack.getDamageValue() > stack.getMaxDamage() / 2) {
						if (level.random.nextDouble() < rumbleChance) {
							// Display a message to the player
							player.displayClientMessage(Component.translatable("hungrytools.tool.hungry", stack.getDisplayName().copy().withStyle(ChatFormatting.GOLD)), true);

							// Play a rumble sound
							level.playSound(null, player.blockPosition(), HungrySounds.HUNGRY.get(), SoundSource.PLAYERS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
							break;
						}
					}
				}
			}
		}

		if (gameTime % (tickRate / 2) == 0) {
			for (InteractionHand hand : InteractionHand.values()) {
				final ItemStack stack = player.getItemInHand(hand);
				// Check if the item is a tool
				if (MenuUtil.isValidTool(stack)) {
					SnackMenu menu = MenuUtil.getSnackMenu(stack);
					if (menu != null) {
						BundleContents contents = stack.getOrDefault(HungryDataComponents.TOOL_CONTENTS, BundleContents.EMPTY);
						if (contents.isEmpty()) {
							if (!level.isClientSide && !stack.has(HungryDataComponents.DESIRED)) {
								if (player.getRandom().nextDouble() < 0.005) {
									// Set a random desired item
									ItemStack desiredStack = menu.getRandomDesirable(player.getRandom());
									if (!desiredStack.isEmpty()) {
										stack.set(HungryDataComponents.DESIRED, Ingredient.of(desiredStack));

										player.displayClientMessage(Component.translatable("hungrytools.tool.desire", stack.getDisplayName().copy().withStyle(ChatFormatting.GOLD), desiredStack.getDisplayName().copy().withStyle(ChatFormatting.GREEN)), true);
									}
								}
							}

							// Do nothing if the tool contains no food
							return;
						}

						BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(contents);
						ItemStack removeStack = bundlecontents$mutable.removeOne();
						// Grab a stack from the tool stomach
						if (removeStack != null) {
							Ingredient desiredIngredient = stack.get(HungryDataComponents.DESIRED);
							if (desiredIngredient != null && desiredIngredient.test(removeStack)) {
								player.sendSystemMessage(Component.translatable("hungrytools.tool.desire.consumed", stack.getDisplayName().copy().withStyle(ChatFormatting.GOLD), removeStack.getDisplayName().copy().withStyle(ChatFormatting.GREEN)));
								stack.remove(HungryDataComponents.DESIRED);
							}

							ItemStack particleStack = removeStack.copy();
							if (level.isClientSide) {
								for (int j = 0; j < 6; j++) {
									level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, particleStack), player.getX(), player.getY() + 0.75, player.getZ(), ((double) level.random.nextFloat() - 0.5) * 0.08, ((double) level.random.nextFloat() - 0.5) * 0.08, ((double) level.random.nextFloat() - 0.5) * 0.08);
								}
							} else {
								if (!level.isClientSide && stack.has(HungryDataComponents.ALLERGIC)) {
									Ingredient allergicIngredient = stack.get(HungryDataComponents.ALLERGIC);
									if (allergicIngredient != null && allergicIngredient.test(removeStack)) {
										player.sendSystemMessage(Component.translatable("hungrytools.tool.allergic", stack.getDisplayName().copy().withStyle(ChatFormatting.GOLD), removeStack.getDisplayName().copy().withStyle(ChatFormatting.RED)));
										player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 300, 2, false, false));
										player.getCooldowns().addCooldown(stack.getItem(), 150);

										if (!stack.has(HungryDataComponents.SHOW_ALLERGY))
											stack.set(HungryDataComponents.SHOW_ALLERGY, true);
									}
								}
								// Reduce damage
								int damageReduction = menu.getFoodValue(removeStack).orElse(0);
								int newDamage = Math.max(0, stack.getDamageValue() - damageReduction);
								stack.setDamageValue(newDamage);

								removeStack.shrink(1);
								if (removeStack.isEmpty() || newDamage == 0) {
									level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
								} else {
									level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
								}

								if (!removeStack.isEmpty()) {
									bundlecontents$mutable.tryInsert(removeStack);
								}
								stack.set(HungryDataComponents.TOOL_CONTENTS, bundlecontents$mutable.toImmutable());
							}
						}
					} else {
						// Drop contents if the tool is not damaged as the tool is no longer hungry
						if (stack.has(HungryDataComponents.TOOL_CONTENTS) && dropContents(stack, player)) {
							player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onTooltip(ItemTooltipEvent event) {
		final ItemStack stack = event.getItemStack();
		List<Component> tooltips = event.getToolTip();
		if (stack.has(HungryDataComponents.TOOL_CONTENTS)) {
			BundleContents bundlecontents = stack.get(HungryDataComponents.TOOL_CONTENTS);
			if (bundlecontents != null) {
				int i = Mth.mulAndTruncate(bundlecontents.weight(), 64);
				tooltips.add(Component.translatable("hungrytools.tool.fullness", i, 64).withStyle(ChatFormatting.GRAY));
			}
		}
		if (stack.has(HungryDataComponents.DESIRED)) {
			Ingredient desiredIngredient = stack.get(HungryDataComponents.DESIRED);
			if (desiredIngredient != null) {
				tooltips.add(Component.translatable("hungrytools.tool.desire.tooltip", desiredIngredient.getItems()[0].getDisplayName()).withStyle(ChatFormatting.GREEN));
			}
		}
		if (stack.has(HungryDataComponents.SHOW_ALLERGY) && stack.has(HungryDataComponents.ALLERGIC)) {
			Ingredient allergicIngredient = stack.get(HungryDataComponents.ALLERGIC);
			if (allergicIngredient != null) {
				tooltips.add(Component.translatable("hungrytools.tool.allergic.tooltip", allergicIngredient.getItems()[0].getDisplayName()).withStyle(ChatFormatting.RED));
			}
		}
	}

	private static boolean dropContents(ItemStack stack, Player player) {
		final BundleContents bundlecontents = stack.get(HungryDataComponents.TOOL_CONTENTS);
		if (bundlecontents != null && !bundlecontents.isEmpty()) {
			stack.set(HungryDataComponents.TOOL_CONTENTS, BundleContents.EMPTY);
			if (player instanceof ServerPlayer) {
				bundlecontents.itemsCopy().forEach(dropped -> {
					player.drop(dropped, true);
				});
			}

			return true;
		} else {
			return false;
		}
	}

	public static void onStackDamage(ItemStack stack, ServerLevel level, @Nullable LivingEntity entity, Consumer<Item> itemConsumer) {
		if (MenuUtil.isValidTool(stack)) {
			HungryTools.LOGGER.debug("Handling stack damage");
			SnackMenu menu = MenuUtil.getSnackMenu(stack);
			if (menu != null && !stack.has(HungryDataComponents.ALLERGIC)) {
				ItemStack randomSnack = menu.getRandomSnack(level.random);
				if (!randomSnack.isEmpty()) {
					HungryTools.LOGGER.info("{} is allergic to {}", stack.getDisplayName().getString(), randomSnack.getDisplayName().getString());
					stack.set(HungryDataComponents.ALLERGIC, Ingredient.of(randomSnack));
				}
			}
		}
	}
}