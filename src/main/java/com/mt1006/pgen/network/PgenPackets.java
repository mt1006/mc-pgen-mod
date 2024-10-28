package com.mt1006.pgen.network;

import com.mt1006.pgen.PgenMod;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PgenPackets
{
	public static void register()
	{
		PayloadTypeRegistry.playS2C().register(PgenPacketS2C.TYPE, PgenPacketS2C.CODEC);
		if (!PgenMod.isDedicatedServer)
		{
			ClientPlayNetworking.registerGlobalReceiver(PgenPacketS2C.TYPE, PgenPackets::clientReceiver);
		}
	}

	private static void clientReceiver(PgenPacketS2C packet, ClientPlayNetworking.Context ctx)
	{
		ctx.client().execute(packet::handle);
	}
}
