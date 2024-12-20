package com.mt1006.pgen.forge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.command.PgenCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PgenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegisterCommands
{
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event)
	{
		PgenCommand.register(event.getDispatcher());
	}
}
