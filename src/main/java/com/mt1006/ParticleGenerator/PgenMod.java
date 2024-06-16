package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.network.PacketHandler;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PgenMod.MOD_ID)
public class PgenMod
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.2";
	public static final String MOD_FOR_VERSION = "1.21";
	public static final String MOD_FOR_LOADER = "Forge";
	public static final Logger LOGGER = LogManager.getLogger();

	public PgenMod()
	{
		LOGGER.info("{} - Author: mt1006", getFullName());
		RegistryHandler.register();
		PacketHandler.register();
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
