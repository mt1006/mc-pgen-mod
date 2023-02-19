package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.pgen.ParticleGeneratorBlock;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

public class ParticleGeneratorPacket
{
	private int operation = 0;
	public static int OP_SHOW = 1;
	public static int OP_HIDE = 2;
	public static int OP_LOCATE = 3;

	public ParticleGeneratorPacket(int operation)
	{
		this.operation = operation;
	}

	public ParticleGeneratorPacket(FriendlyByteBuf buf)
	{
		operation = buf.readInt();
	}

	public FriendlyByteBuf encode(FriendlyByteBuf buf)
	{
		buf.writeInt(operation);
		return buf;
	}

	public void handle()
	{
		if (operation == OP_SHOW) { ParticleGeneratorBlock.showShape = true; }
		else if (operation == OP_HIDE) { ParticleGeneratorBlock.showShape = false; }
		else if (operation == OP_LOCATE) { ParticleGeneratorBlock.locate(); }
	}

	public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender)
	{
		(new ParticleGeneratorPacket(buf)).handle();
	}
}
