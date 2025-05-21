package com.mrbysco.hungrytools.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class HungryConfig {
	public static class Common {
		public final ModConfigSpec.BooleanValue stomachRumbling;
		public final ModConfigSpec.DoubleValue rumbleChance;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("general");

			stomachRumbling = builder.comment("Should the tools stomach rumble every so often when hungry? [Default: true]")
					.define("stomachRumbling", true);
			rumbleChance = builder.comment("Chance of the stomach rumbling when hungry (Checked every second, 0.1 = 10% chance) [Default: 0.005]")
					.defineInRange("rumbleChance", 0.005, 0, 1);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}
