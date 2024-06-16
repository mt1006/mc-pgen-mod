package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.command.RegisterCommands;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PgenMod implements ModInitializer
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.2";
	public static final String MOD_FOR_VERSION = "1.21";
	public static final String MOD_FOR_LOADER = "Fabric";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final boolean isDedicatedServer = FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;

	@Override public void onInitialize()
	{
		RegistryHandler.register();
		RegisterCommands.registerCommands();
		PgenMod.LOGGER.info("{} - Author: mt1006", getFullName());
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
