package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.command.RegisterCommands;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParticleGenerator implements ModInitializer
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.1";
	public static final String MOD_FOR_VERSION = "1.19.3";
	public static final String MOD_FOR_LOADER = "Fabric";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize()
	{
		RegistryHandler.register();
		RegisterCommands.registerCommands();
		ParticleGenerator.LOGGER.info(getFullName() + " - Author: mt1006");
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
