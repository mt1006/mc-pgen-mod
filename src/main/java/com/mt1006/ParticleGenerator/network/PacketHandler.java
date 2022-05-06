package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.ParticleGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler
{
	private static final String PROTOCOL_VERSION = "1";
	private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ParticleGenerator.MOD_ID, "main"),
			() -> PROTOCOL_VERSION, (str) -> true, (str) -> true);
	private static int index = 0;

	public static void register()
	{
		INSTANCE.messageBuilder(ParticleGeneratorPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
				.decoder(ParticleGeneratorPacket::new)
				.encoder(ParticleGeneratorPacket::encode)
				.consumer(ParticleGeneratorPacket::handle)
				.add();
	}

	public static void sendToClient(ParticleGeneratorPacket msg, ServerPlayer serverPlayer)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
	}
}
