package com.mt1006.pgen.forge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PgenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterBlockEntityRenderer
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(RegistryHandler.BLOCK_ENTITY_PG.get(), ParticleGeneratorBlockEntityRenderer::new);
	}
}
