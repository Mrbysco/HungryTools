package com.mrbysco.hungrytools.registry;

import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mrbysco.hungrytools.HungryTools;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HungryDataComponents {
	public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, HungryTools.MOD_ID);

	public static final Supplier<DataComponentType<Ingredient>> DESIRED = DATA_COMPONENT_TYPES.register("desired", () ->
			DataComponentType.<Ingredient>builder()
					.persistent(Ingredient.CODEC_NONEMPTY)
					.networkSynchronized(Ingredient.CONTENTS_STREAM_CODEC)
					.build()
	);

	public static final Supplier<DataComponentType<Ingredient>> ALLERGIC = DATA_COMPONENT_TYPES.register("allergic", () ->
			DataComponentType.<Ingredient>builder()
					.persistent(Ingredient.CODEC_NONEMPTY)
					.networkSynchronized(Ingredient.CONTENTS_STREAM_CODEC)
					.build()
	);

	public static final Supplier<DataComponentType<Boolean>> SHOW_ALLERGY = DATA_COMPONENT_TYPES.register("show_allergy", () ->
			DataComponentType.<Boolean>builder()
					.persistent(PrimitiveCodec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL)
					.build()
	);

	public static final Supplier<DataComponentType<BundleContents>> TOOL_CONTENTS = DATA_COMPONENT_TYPES.register("tool_contents", () ->
			DataComponentType.<BundleContents>builder()
					.persistent(BundleContents.CODEC)
					.networkSynchronized(BundleContents.STREAM_CODEC)
					.cacheEncoding()
					.build()
	);
}
