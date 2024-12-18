package com.mt1006.pgen.particles;

import com.mt1006.pgen.PgenMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LocateParticle extends TextureSheetParticle
{
	private LocateParticle(ClientLevel level, double x, double y, double z, Item item)
	{
		super(level, x, y, z);

		BakedModel itemModel = Minecraft.getInstance().getItemRenderer().getModel(new ItemStack(item), null, null, 0);
		setSprite(itemModel.getParticleIcon());

		gravity = 0.0f;
		lifetime = 80;
		hasPhysics = false;
	}

	@Override public @NotNull ParticleRenderType getRenderType()
	{
		return ParticleRenderType.TERRAIN_SHEET;
	}

	@Override public float getQuadSize(float arg)
	{
		return 0.5f;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType>
	{
		public Provider() {}

		@Override public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level,
												 double x, double y, double z, double mx, double my, double mz)
		{
			return new LocateParticle(level, x, y, z, PgenMod.loaderInterface.getBlock().asItem());
		}
	}
}
