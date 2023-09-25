package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ParticleGenerator.MOD_ID)
@Mod.EventBusSubscriber(modid = ParticleGenerator.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleGenerator
{
	public static final String MOD_ID = "pgen";
	public static final String VERSION = "1.0.1";
	public static final String MOD_FOR_VERSION = "1.20.2";
	public static final String MOD_FOR_LOADER = "Forge";
	public static final Logger LOGGER = LogManager.getLogger();

	public ParticleGenerator()
	{
		MinecraftForge.EVENT_BUS.register(this);
		RegistryHandler.register();
		PacketHandler.register();
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event)
	{
		LOGGER.info(getFullName() + " - Author: mt1006 (mt1006x)");
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
