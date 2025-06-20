package com.mt1006.pgen.neoforge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.command.PgenCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = PgenMod.MOD_ID)
public class RegisterCommands
{
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event)
	{
		PgenCommand.register(event.getDispatcher());
	}
}
