package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.PgenMod;
import com.mt1006.ParticleGenerator.pgen.ParticleGeneratorBlock;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class PgenPacketS2C implements CustomPacketPayload
{
	public static final int OP_SHOW = 1;
	public static final int OP_HIDE = 2;
	public static final int OP_LOCATE = 3;
	private final int operation;

	public static final CustomPacketPayload.Type<PgenPacketS2C> TYPE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PgenMod.MOD_ID, "neoforge_s2c"));

	public static final StreamCodec<ByteBuf, PgenPacketS2C> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, (packet) -> packet.operation, PgenPacketS2C::new);

	public PgenPacketS2C(int operation)
	{
		this.operation = operation;
	}

	@Override public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type()
	{
		return TYPE;
	}

	public static void handle(PgenPacketS2C packet, IPayloadContext ctx)
	{
		switch (packet.operation)
		{
			case OP_SHOW -> ParticleGeneratorBlock.showShape = true;
			case OP_HIDE -> ParticleGeneratorBlock.showShape = false;
			case OP_LOCATE -> ParticleGeneratorBlock.locate();
		}
	}

	public static void send(ServerPlayer serverPlayer, int op)
	{
		PacketHandler.sendToClient(new PgenPacketS2C(op), serverPlayer);
	}
}
