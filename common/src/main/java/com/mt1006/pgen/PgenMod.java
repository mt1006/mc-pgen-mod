package com.mt1006.pgen;

public class PgenMod
{
	public static final String MOD_ID = "pgen";
	public static final String FOR_VERSION = "1.21.1";
	public static boolean isDedicatedServer = false;
	public static PgenModLoaderInterface loaderInterface = null;

	public static void init(boolean isDedicatedServer, PgenModLoaderInterface loaderInterface)
	{
		PgenMod.isDedicatedServer = isDedicatedServer;
		PgenMod.loaderInterface = loaderInterface;
	}

	public static String getName()
	{
		return String.format("ParticleGenerator v%s", loaderInterface.getModVersion());
	}

	public static String getFullName()
	{
		return String.format("ParticleGenerator v%s for Minecraft %s [%s]",
				loaderInterface.getModVersion(), FOR_VERSION, loaderInterface.getLoaderName());
	}
}
