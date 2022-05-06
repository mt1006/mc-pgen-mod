package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.block.ParticleGeneratorBlock;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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

	public ParticleGeneratorPacket(PacketBuffer buf)
	{
		operation = buf.readInt();
	}

	public void encode(PacketBuffer buf)
	{
		buf.writeInt(operation);
	}

	public void handle(Supplier<NetworkEvent.Context> ctx)
	{
		if (operation == OP_SHOW) { ParticleGeneratorBlock.showShape = true; }
		else if (operation == OP_HIDE) { ParticleGeneratorBlock.showShape = false; }
		else if (operation == OP_LOCATE) { ParticleGeneratorBlock.locate(); }
	}
}
