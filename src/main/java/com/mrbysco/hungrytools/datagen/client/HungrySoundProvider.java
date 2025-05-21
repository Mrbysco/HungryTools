package com.mrbysco.hungrytools.datagen.client;

import com.mrbysco.hungrytools.HungryTools;
import com.mrbysco.hungrytools.registry.HungrySounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class HungrySoundProvider extends SoundDefinitionsProvider {

	public HungrySoundProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, HungryTools.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerSounds() {
		this.add(HungrySounds.HUNGRY, definition()
				.subtitle(modSubtitle(HungrySounds.HUNGRY.getId()))
				.with(sound(modLoc("stomach_rumble"))));
	}


	public String modSubtitle(ResourceLocation id) {
		return HungryTools.MOD_ID + ".subtitle." + id.getPath();
	}

	public ResourceLocation modLoc(String name) {
		return HungryTools.modLoc(name);
	}
}
