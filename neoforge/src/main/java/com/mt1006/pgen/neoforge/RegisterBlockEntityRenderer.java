package com.mt1006.pgen.neoforge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = PgenMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterBlockEntityRenderer
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(RegistryHandler.BLOCK_ENTITY_PG.get(), ParticleGeneratorBlockEntityRenderer::new);
	}
}
