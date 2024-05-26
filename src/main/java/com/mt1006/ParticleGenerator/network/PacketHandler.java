package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.PgenMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;

public class PacketHandler
{
	public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(PgenMod.MOD_ID, "forge")).simpleChannel();
	private static int index = 0;

	public static void register()
	{
		INSTANCE.messageBuilder(PgenPacketS2C.class, index++, NetworkDirection.PLAY_TO_CLIENT)
				.decoder(PgenPacketS2C::new)
				.encoder(PgenPacketS2C::encode)
				.consumerMainThread(PgenPacketS2C::handle)
				.add();
	}

	public static void sendToClient(PgenPacketS2C msg, ServerPlayer serverPlayer)
	{
		INSTANCE.send(msg, PacketDistributor.PLAYER.with(serverPlayer));
	}
}
