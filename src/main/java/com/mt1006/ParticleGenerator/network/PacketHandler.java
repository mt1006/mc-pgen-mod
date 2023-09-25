package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.ParticleGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;

public class PacketHandler
{
	public static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(ParticleGenerator.MOD_ID, "forge")).simpleChannel();
	private static int index = 0;

	public static void register()
	{
		INSTANCE.messageBuilder(ParticleGeneratorPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
				.decoder(ParticleGeneratorPacket::new)
				.encoder(ParticleGeneratorPacket::encode)
				.consumerMainThread(ParticleGeneratorPacket::handle)
				.add();
	}

	public static void sendToClient(ParticleGeneratorPacket msg, ServerPlayer serverPlayer)
	{
		INSTANCE.send(msg, PacketDistributor.PLAYER.with(serverPlayer));
	}
}
