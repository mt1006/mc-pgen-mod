package com.mt1006.ParticleGenerator.command;

import com.mt1006.ParticleGenerator.ParticleGenerator;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = ParticleGenerator.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterCommands
{
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event)
	{
		ParticleGeneratorCommand.register(event.getDispatcher());
		ConfigCommand.register(event.getDispatcher());
	}
}
