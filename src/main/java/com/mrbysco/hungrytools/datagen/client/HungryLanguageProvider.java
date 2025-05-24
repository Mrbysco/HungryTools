package com.mrbysco.hungrytools.datagen.client;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.registry.HungrySounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class HungryLanguageProvider extends LanguageProvider {
	public HungryLanguageProvider(PackOutput output) {
		super(output, HungryTools.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.add("hungrytools.tool.fullness", "%s/%s");
		this.add("hungrytools.tool.hungry", "Your %s is hungry");
		this.add("hungrytools.tool.allergic", "Your %s is allergic to %s");
		this.add("hungrytools.tool.allergic.tooltip", "Allergic to %s");
		this.add("hungrytools.tool.desire", "Your %s wants to eat a %s");
		this.add("hungrytools.tool.desire.consumed", "Your %s enjoyed eating %s");
		this.add("hungrytools.tool.desire.tooltip", "Desires to eat %s");
		this.add("hungrytools.insertion.desired_fail", "This tool desires a different item");
		this.add("hungrytools.insertion.fail", "This is not the right food for a tool like this");

		this.add("hungrytools.tooltip.snack", "This tool may consume these snacks");
		this.add("hungrytools.tooltip.desirable", "This tool may desire these snacks");

		this.addSubtitle(HungrySounds.HUNGRY, "Rumbling of a tool's stomach");

		this.addConfig("general", "General", "General settings");
		this.addConfig("stomachRumbling", "Stomach Rumbling", "Should the tools stomach rumble every so often when hungry? [Default: true]");
		this.addConfig("rumbleChance", "Rumble Chance", "Chance of the stomach rumbling when hungry (Checked every second, 0.1 = 10% chance) [Default: 0.005]");
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event
	 * @param text  The subtitle text
	 */
	public void addSubtitle(Supplier<SoundEvent> sound, String text) {
		this.addSubtitle(sound.get(), text);
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	public void addSubtitle(SoundEvent sound, String text) {
		String path = HungryTools.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, text);
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add("hungrytools.configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add("hungrytools.configuration." + path + ".tooltip", description);
	}
}
