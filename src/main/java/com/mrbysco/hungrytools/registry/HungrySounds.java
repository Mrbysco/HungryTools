package com.mrbysco.hungrytools.registry;

import com.mrbysco.hungrytools.HungryTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HungrySounds {

	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, HungryTools.MOD_ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> HUNGRY = registerSound("hungry");

	private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(HungryTools.modLoc(name)));
	}
}
