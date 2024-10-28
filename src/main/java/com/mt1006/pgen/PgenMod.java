package com.mt1006.pgen;

import com.mt1006.pgen.command.RegisterCommands;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class PgenMod implements ModInitializer
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.3";
	public static final String MOD_FOR_VERSION = "1.21.3";
	public static final String MOD_FOR_LOADER = "Fabric";
	public static final boolean isDedicatedServer = FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;

	@Override public void onInitialize()
	{
		RegistryHandler.register();
		RegisterCommands.registerCommands();
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
