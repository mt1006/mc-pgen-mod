package com.mt1006.pgen.command;

import com.mt1006.pgen.PgenMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = PgenMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class RegisterCommands
{
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event)
	{
		PgenCommand.register(event.getDispatcher());
		ConfigCommand.register(event.getDispatcher());
	}
}
