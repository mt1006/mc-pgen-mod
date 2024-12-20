package com.mt1006.pgen.network;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PgenPacketS2C
{
	public static final int OP_SHOW = 1;
	public static final int OP_HIDE = 2;

	private final int operation;

	public PgenPacketS2C(int operation)
	{
		this.operation = operation;
	}

	public PgenPacketS2C(FriendlyByteBuf buf)
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
		switch (operation)
		{
			case OP_SHOW -> ParticleGeneratorBlock.showShape = true;
			case OP_HIDE -> ParticleGeneratorBlock.showShape = false;
		}
	}

	public static void send(ServerPlayer player, int op)
	{
		PgenMod.loaderInterface.sendPacketToClient(player, new PgenPacketS2C(op));
	}
}
