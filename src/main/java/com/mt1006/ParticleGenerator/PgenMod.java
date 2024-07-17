package com.mt1006.ParticleGenerator;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PgenMod.MOD_ID)
public class PgenMod
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.2";
	public static final String MOD_FOR_VERSION = "1.21";
	public static final String MOD_FOR_LOADER = "NeoForge";
	public static final Logger LOGGER = LogManager.getLogger();

	public PgenMod(IEventBus eventBus)
	{
		LOGGER.info("{} - Author: mt1006", getFullName());
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
