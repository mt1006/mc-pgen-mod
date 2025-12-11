package com.mt1006.pgen.network;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class PgenPacketS2C implements CustomPacketPayload
{
	private static final String TYPE_ID = PgenMod.loaderInterface.getLoaderName().toLowerCase() + "_s2c";
	public static final Type<PgenPacketS2C> TYPE = new Type<>(Identifier.fromNamespaceAndPath(PgenMod.MOD_ID, TYPE_ID));
	public static final StreamCodec<FriendlyByteBuf, PgenPacketS2C> CODEC = StreamCodec.of((b, p) -> p.encode(b), PgenPacketS2C::new);

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

	@Override public @NotNull Type<? extends CustomPacketPayload> type()
	{
		return TYPE;
	}

	public void encode(FriendlyByteBuf buf)
	{
		buf.writeInt(operation);
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
