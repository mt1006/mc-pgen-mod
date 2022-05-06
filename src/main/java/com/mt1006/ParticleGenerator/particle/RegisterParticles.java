package com.mt1006.ParticleGenerator.particle;

import com.mt1006.ParticleGenerator.ParticleGenerator;
import com.mt1006.ParticleGenerator.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ParticleGenerator.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterParticles
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticles(ParticleFactoryRegisterEvent event)
	{
		Minecraft.getInstance().particleEngine.register(RegistryHandler.PARTICLE_LOCATE.get(), new LocateParticle.Factory());
	}
}
