package com.mrbysco.hungrytools.mixin;

import com.mrbysco.hungrytools.handler.ToolHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {


	@Inject(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V",
					shift = Shift.AFTER
			), cancellable = true
	)
	public void hungrytools$hurtAndBreak(int damage, ServerLevel serverLevel, LivingEntity livingEntity, Consumer<Item> itemConsumer, CallbackInfo ci) {
		ItemStack stack = (ItemStack) (Object) this;
		ToolHandler.onStackDamage(stack, serverLevel, livingEntity, itemConsumer);
	}
}
