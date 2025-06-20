package com.mt1006.pgen.neoforge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.network.PgenPacketS2C;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = PgenMod.MOD_ID)
public class PacketHandler
{
	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event)
	{
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToClient(PgenPacketS2C.TYPE, PgenPacketS2C.CODEC, PacketHandler::clientReceiver);
	}

	private static void clientReceiver(PgenPacketS2C packet, IPayloadContext ctx)
	{
		packet.handle();
	}
}
