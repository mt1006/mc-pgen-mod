package com.mt1006.pgen.forge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.particles.LocateParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PgenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterParticles
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticles(RegisterParticleProvidersEvent event)
	{
		Minecraft.getInstance().particleEngine.register(RegistryHandler.PARTICLE_LOCATE.get(), new LocateParticle.Provider());
	}
}
