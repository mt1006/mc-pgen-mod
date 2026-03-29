package com.mt1006.pgen.fabric;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.network.PgenPacketS2C;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PacketHandler
{
	public static void register()
	{
		PayloadTypeRegistry.clientboundPlay().register(PgenPacketS2C.TYPE, PgenPacketS2C.CODEC);
		if (!PgenMod.isDedicatedServer)
		{
			ClientPlayNetworking.registerGlobalReceiver(PgenPacketS2C.TYPE, PacketHandler::clientReceiver);
		}
	}

	private static void clientReceiver(PgenPacketS2C packet, ClientPlayNetworking.Context ctx)
	{
		ctx.client().execute(packet::handle);
	}
}
