package com.mt1006.pgen;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(PgenMod.MOD_ID)
public class PgenMod
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.3";
	public static final String MOD_FOR_VERSION = "1.21.3";
	public static final String MOD_FOR_LOADER = "NeoForge";

	public PgenMod(IEventBus eventBus)
	{
		RegistryHandler.register(eventBus);
	}

	public static String getName()
	{
		return "ParticleGenerator v" + VERSION;
	}

	public static String getFullName()
	{
		return "ParticleGenerator v" + VERSION + " for Minecraft " + MOD_FOR_VERSION + " [" + MOD_FOR_LOADER + "]";
	}
}
