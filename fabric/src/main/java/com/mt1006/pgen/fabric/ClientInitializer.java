package com.mt1006.pgen.fabric;

import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class ClientInitializer implements ClientModInitializer
{
	@Override public void onInitializeClient()
	{
		RegisterParticles.registerParticles();
		BlockEntityRenderers.register(RegistryHandler.BLOCK_ENTITY_PG, ParticleGeneratorBlockEntityRenderer::new);

		PacketHandler.register();
		BlockRenderLayerMap.INSTANCE.putBlock(RegistryHandler.BLOCK_PG, RenderType.cutoutMipped());
	}
}
