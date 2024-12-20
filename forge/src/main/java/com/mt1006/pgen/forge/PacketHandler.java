package com.mt1006.pgen.forge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.network.PgenPacketS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketHandler
{
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(PgenMod.MOD_ID, "main"),
			() -> PROTOCOL_VERSION, (str) -> true, (str) -> true);
	private static int index = 0;

	public static void register()
	{
		INSTANCE.messageBuilder(PgenPacketS2C.class, index++, NetworkDirection.PLAY_TO_CLIENT)
				.decoder(PgenPacketS2C::new)
				.encoder(PgenPacketS2C::encode)
				.consumerMainThread(PacketHandler::clientReceiver)
				.add();
	}

	private static void clientReceiver(PgenPacketS2C packet, Supplier<NetworkEvent.Context> ctx)
	{
		packet.handle();
	}
}
