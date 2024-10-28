package com.mt1006.pgen;

import com.mt1006.pgen.network.PgenPackets;
import com.mt1006.pgen.particles.RegisterParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class ClientEvents implements ClientModInitializer
{
	@Override public void onInitializeClient()
	{
		RegisterParticles.registerParticles();
		PgenPackets.register();
		BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.BLOCK_PG, RenderType.cutoutMipped());
	}
}