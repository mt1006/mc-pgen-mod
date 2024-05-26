package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.pgen.ParticleGeneratorBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class PgenPacketS2C
{
	public static int OP_SHOW = 1;
	public static int OP_HIDE = 2;
	public static int OP_LOCATE = 3;
	private final int operation;

	public PgenPacketS2C(int operation)
	{
		this.operation = operation;
	}

	public PgenPacketS2C(FriendlyByteBuf buf)
	{
		operation = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(operation);
	}

	public void handle(CustomPayloadEvent.Context ctx)
	{
		if (operation == OP_SHOW) { ParticleGeneratorBlock.showShape = true; }
		else if (operation == OP_HIDE) { ParticleGeneratorBlock.showShape = false; }
		else if (operation == OP_LOCATE) { ParticleGeneratorBlock.locate(); }
	}

	public static void send(ServerPlayer serverPlayer, int op)
	{
		PacketHandler.sendToClient(new PgenPacketS2C(op), serverPlayer);
	}
}
