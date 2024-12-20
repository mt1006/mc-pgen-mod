package com.mt1006.pgen.fabric;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.network.PgenPacketS2C;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler
{
	public static final ResourceLocation CHANNEL_NAME = new ResourceLocation(PgenMod.MOD_ID, "fabric");

	public static void register()
	{
		ClientPlayNetworking.registerGlobalReceiver(CHANNEL_NAME, PacketHandler::clientReceiver);
	}

	private static void clientReceiver(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender)
	{
		new PgenPacketS2C(buf).handle();
	}
}
