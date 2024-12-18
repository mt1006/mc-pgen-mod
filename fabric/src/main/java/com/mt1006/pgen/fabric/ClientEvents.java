package com.mt1006.pgen.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class ClientEvents implements ClientModInitializer
{
	@Override public void onInitializeClient()
	{
		RegisterParticles.registerParticles();
		PacketHandler.register();
		BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.BLOCK_PG, RenderType.cutoutMipped());
	}
}