package com.mt1006.ParticleGenerator.network;

import com.mt1006.ParticleGenerator.PgenMod;
import com.mt1006.ParticleGenerator.pgen.ParticleGeneratorBlock;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class PgenPacketS2C implements CustomPacketPayload
{
	public static final Type<PgenPacketS2C> TYPE = new Type<>(new ResourceLocation(PgenMod.MOD_ID, "fabric_s2c"));
	public static final StreamCodec<FriendlyByteBuf, PgenPacketS2C> CODEC = StreamCodec.of((b, p) -> p.encode(b), PgenPacketS2C::new);

	public static final int OP_SHOW = 1;
	public static final int OP_HIDE = 2;
	public static final int OP_LOCATE = 3;

	private final int operation;

	private PgenPacketS2C(int operation)
	{
		this.operation = operation;
	}

	private PgenPacketS2C(FriendlyByteBuf buf)
	{
		operation = buf.readInt();
	}

	@Override public @NotNull Type<? extends CustomPacketPayload> type()
	{
		return TYPE;
	}

	private void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(operation);
	}

	public void handle()
	{
		switch (operation)
		{
			case OP_SHOW: ParticleGeneratorBlock.showShape = true; break;
			case OP_HIDE: ParticleGeneratorBlock.showShape = false; break;
			case OP_LOCATE: ParticleGeneratorBlock.locate(); break;
		}
	}

	public static void send(ServerPlayer serverPlayer, int op)
	{
		ServerPlayNetworking.send(serverPlayer, new PgenPacketS2C(op));
	}
}
