package com.mt1006.ParticleGenerator;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ParticleGenerator.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents
{
	@SubscribeEvent
	public static void clientSetup(final FMLClientSetupEvent event)
	{
		// 1.18.2 and older
		//ItemBlockRenderTypes.setRenderLayer(RegistryHandler.BLOCK_PG.get(), RenderType.cutoutMipped());
	}
}