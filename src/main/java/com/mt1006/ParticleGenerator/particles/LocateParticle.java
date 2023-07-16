package com.mt1006.ParticleGenerator.particles;

import com.mt1006.ParticleGenerator.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LocateParticle extends TextureSheetParticle
{
	private LocateParticle(ClientLevel clientLevel, double x, double y, double z, Item itemProvider)
	{
		super(clientLevel, x, y, z);

		BakedModel itemModel = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(itemProvider);
		if (itemModel == null) { return; }

		setSprite(itemModel.getParticleIcon());

		gravity = 0.0F;
		lifetime = 80;
		hasPhysics = false;
	}

	@Override
	public @NotNull ParticleRenderType getRenderType()
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
		public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel clientLevel,
									   double x, double y, double z, double mx, double my, double mz)
		{
			return new LocateParticle(clientLevel, x, y, z, RegistryHandler.BLOCK_PG.get().asItem());
		}
	}
}
