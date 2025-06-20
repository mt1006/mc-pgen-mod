package com.mt1006.pgen.fabric;

import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class ClientInitializer implements ClientModInitializer
{
	@Override public void onInitializeClient()
	{
		BlockEntityRenderers.register(RegistryHandler.BLOCK_ENTITY_PG, ParticleGeneratorBlockEntityRenderer::new);

		PacketHandler.register();
		BlockRenderLayerMap.putBlock(RegistryHandler.BLOCK_PG, ChunkSectionLayer.CUTOUT_MIPPED);
	}
}
