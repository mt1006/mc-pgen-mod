package com.mt1006.pgen.network;

import com.mt1006.pgen.PgenMod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = PgenMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PacketHandler
{
	public static final CustomPacketPayload.Type<CustomPacketPayload> INSTANCE =
			new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PgenMod.MOD_ID, "neoforge"));

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event)
	{
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToClient(PgenPacketS2C.TYPE, PgenPacketS2C.STREAM_CODEC, PgenPacketS2C::handle);
	}

	public static void sendToClient(PgenPacketS2C msg, ServerPlayer serverPlayer)
	{
		PacketDistributor.sendToPlayer(serverPlayer, msg);
	}
}
