package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.ParticleGenerator;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class PacketHandler
{
	private static final ResourceLocation CHANNEL_NAME = new ResourceLocation(ParticleGenerator.MOD_ID, "main");

	public static void register()
	{
		ClientPlayNetworking.registerGlobalReceiver(CHANNEL_NAME, ParticleGeneratorPacket::receive);
	}

	public static void sendToClient(ParticleGeneratorPacket msg, ServerPlayer serverPlayer)
	{
		ServerPlayNetworking.send(serverPlayer, CHANNEL_NAME, msg.encode(PacketByteBufs.create()));
	}
}
