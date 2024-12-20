package com.mt1006.pgen.forge;

import com.mt1006.pgen.network.PgenPacketS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler
{
	public static final SimpleChannel INSTANCE =
			ChannelBuilder.named(new ResourceLocation(com.mt1006.pgen.PgenMod.MOD_ID, "forge")).simpleChannel();
	private static int index = 0;

	public static void register()
	{
		INSTANCE.messageBuilder(PgenPacketS2C.class, index++, NetworkDirection.PLAY_TO_CLIENT)
				.decoder(PgenPacketS2C::new)
				.encoder(PgenPacketS2C::encode)
				.consumerMainThread(PacketHandler::clientReceiver)
				.add();
	}

	private static void clientReceiver(PgenPacketS2C packet, CustomPayloadEvent.Context ctx)
	{
		packet.handle();
	}
}
