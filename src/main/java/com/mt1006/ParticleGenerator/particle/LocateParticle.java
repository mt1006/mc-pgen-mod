package com.mt1006.ParticleGenerator.particle;

import com.mt1006.ParticleGenerator.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LocateParticle extends SpriteTexturedParticle
{
	private LocateParticle(ClientWorld clientWorld, double x, double y, double z, IItemProvider itemProvider)
	{
		super(clientWorld, x, y, z);
		this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getParticleIcon(itemProvider));
		this.gravity = 0.0F;
		this.lifetime = 80;
		this.hasPhysics = false;
	}

	@Override
	public IParticleRenderType getRenderType()
	{
		return IParticleRenderType.TERRAIN_SHEET;
	}

	@Override
	public float getQuadSize(float arg)
	{
		return 0.5F;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType>
	{
		public Factory() {}

		@Override
		public Particle createParticle(BasicParticleType particleType, ClientWorld clientWorld,
									   double x, double y, double z, double mx, double my, double mz)
		{
			return new LocateParticle(clientWorld, x, y, z, RegistryHandler.BLOCK_PG.get().asItem());
		}
	}
}
