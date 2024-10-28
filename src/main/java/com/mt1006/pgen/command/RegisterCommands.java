package com.mt1006.pgen.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class RegisterCommands
{
	public static void registerCommands()
	{
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> PgenCommand.register(dispatcher));
	}
}
