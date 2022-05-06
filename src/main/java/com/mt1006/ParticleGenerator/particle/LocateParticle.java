package com.mt1006.ParticleGenerator.particle;

import com.mt1006.ParticleGenerator.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LocateParticle extends TextureSheetParticle
{
	private LocateParticle(ClientLevel clientWorld, double x, double y, double z, Item itemProvider)
	{
		super(clientWorld, x, y, z);
		this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(itemProvider).getParticleIcon());
		this.gravity = 0.0F;
		this.lifetime = 80;
		this.hasPhysics = false;
	}

	@Override
	public ParticleRenderType getRenderType()
	{
		return ParticleRenderType.TERRAIN_SHEET;
	}

	@Override
	public float getQuadSize(float arg)
	{
		return 0.5F;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType>
	{
		public Factory() {}

		@Override
		public Particle createParticle(SimpleParticleType particleType, ClientLevel clientWorld,
									   double x, double y, double z, double mx, double my, double mz)
		{
			return new LocateParticle(clientWorld, x, y, z, RegistryHandler.BLOCK_PG.get().asItem());
		}
	}
}
