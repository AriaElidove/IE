/*
 * BluSunrize
 * Copyright (c) 2022
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.register;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class IEStats
{
	private static final DeferredRegister<ResourceLocation> REGISTER = DeferredRegister.create(
			Registries.CUSTOM_STAT, ImmersiveEngineering.MODID
	);
	private static final List<Runnable> RUN_IN_SETUP = new ArrayList<>();

	public static final Holder<ResourceLocation> WIRE_DEATHS = registerCustomStat("wire_deaths", StatFormatter.DEFAULT);
	public static final Holder<ResourceLocation> SKYHOOK_DISTANCE = registerCustomStat("skyhook_distance", StatFormatter.DISTANCE);

	public static void modConstruction(IEventBus modBus)
	{
		REGISTER.register(modBus);
	}

	public static void setup()
	{
		RUN_IN_SETUP.forEach(Runnable::run);
	}

	private static Holder<ResourceLocation> registerCustomStat(String name, StatFormatter formatter)
	{
		return REGISTER.register(name, () -> {
			ResourceLocation regName = ImmersiveEngineering.rl(name);
			RUN_IN_SETUP.add(() -> Stats.CUSTOM.get(regName, formatter));
			return regName;
		});
	}
}
