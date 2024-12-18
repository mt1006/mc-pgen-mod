package com.mt1006.pgen.neoforge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.particles.LocateParticle;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = PgenMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterParticles
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticles(RegisterParticleProvidersEvent event)
	{
		Minecraft.getInstance().particleEngine.register(RegistryHandler.PARTICLE_LOCATE.get(), new LocateParticle.Provider());
	}
}
