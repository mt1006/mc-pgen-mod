package com.mt1006.pgen.fabric;

import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class ClientInitializer implements ClientModInitializer
{
	@Override public void onInitializeClient()
	{
		BlockEntityRenderers.register(RegistryHandler.BLOCK_ENTITY_PG, ParticleGeneratorBlockEntityRenderer::new);
		PacketHandler.register();
	}
}
