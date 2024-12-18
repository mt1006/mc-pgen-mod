package com.mt1006.pgen.fabric;

import com.mt1006.pgen.particles.LocateParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class RegisterParticles
{
	public static void registerParticles()
	{
		ParticleFactoryRegistry.getInstance().register(RegistryHandler.PARTICLE_LOCATE, new LocateParticle.Provider());
	}
}
