package com.mt1006.ParticleGenerator.util;

import com.mojang.math.Vector3d;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Random;

public class GeneratedParticleInfo
{
	private ParticleOptions particle = null;
	private Vector3d motion = new Vector3d(0.0D, 0.0D, 0.0D);
	private Vector3d motionRand = new Vector3d(0.0D, 0.0D, 0.0D);
	private Vector3d posOffset = new Vector3d(0.0D, 0.0D, 0.0D);
	private Vector3d posRand = new Vector3d(0.0D, 0.0D, 0.0D);
	private int interval = 1;
	private double probability = 1.0;
	private int particleCount = 1;
	private int particleMaxCount = 1;
	private CompoundTag additionalTags = null;
	private boolean useRand = false;
	private int intervalCounter = 0;
	private final ParticleType[] BLOCK_PARTICLES = {ParticleTypes.BLOCK, ParticleTypes.BLOCK_MARKER, ParticleTypes.FALLING_DUST};
	private final ParticleType[] ITEM_PARTICLES = {ParticleTypes.ITEM};

	public GeneratedParticleInfo(CompoundTag nbt)
	{
		load(nbt);
	}

	public void load(CompoundTag nbt)
	{
		if (nbt.contains("id"))
		{
			ResourceLocation resLoc = Utils.resourceLocationFromString(nbt.getString("id"));
			ParticleType particleType = ForgeRegistries.PARTICLE_TYPES.getValue(resLoc);
			if (particleType != null)
			{
				if (particleType instanceof ParticleOptions) { particle = (ParticleOptions)particleType; }
				else { particle = loadComplexParticle(particleType, nbt); }
			}
		}
		if (nbt.contains("Motion")) { motion = Utils.newVector3dFromNBT(nbt, "Motion"); }
		if (nbt.contains("MotionRand")) { motionRand = Utils.newVector3dFromNBT(nbt, "MotionRand"); }
		if (nbt.contains("PositionOffset")) { posOffset = Utils.newVector3dFromNBT(nbt, "PositionOffset"); }
		if (nbt.contains("PositionRand")) { posRand = Utils.newVector3dFromNBT(nbt, "PositionRand"); }
		if (nbt.contains("Interval")) { interval = nbt.getInt("Interval"); }
		if (nbt.contains("Probability")) { probability = nbt.getDouble("Probability"); }
		if (nbt.contains("ParticleCount")) { particleCount = nbt.getInt("ParticleCount"); }
		if (nbt.contains("ParticleMaxCount")) { particleMaxCount = nbt.getInt("ParticleMaxCount"); }
		if (!motionRand.equals(new Vector3d(0.0D, 0.0D, 0.0D)) || !posRand.equals(new Vector3d(0.0D, 0.0D, 0.0D))) { useRand = true; }
	}

	private ParticleOptions loadComplexParticle(ParticleType particleType, CompoundTag nbt)
	{
		CompoundTag additionalTags = null;
		if (nbt.contains("AdditionalTags"))
		{
			additionalTags = nbt.getCompound("AdditionalTags");
			this.additionalTags = additionalTags.copy();
		}
		if (Arrays.asList(BLOCK_PARTICLES).contains(particleType))
		{
			Block block = null;
			if (additionalTags != null && additionalTags.contains("id"))
			{
				ResourceLocation resLoc = Utils.resourceLocationFromString(additionalTags.getString("id"));
				block = ForgeRegistries.BLOCKS.getValue(resLoc);
			}
			if (block == null) { block = Blocks.AIR; }
			return new BlockParticleOption(particleType, block.defaultBlockState());
		}
		else if (Arrays.asList(ITEM_PARTICLES).contains(particleType))
		{
			Item item = null;
			if (additionalTags != null && additionalTags.contains("id"))
			{
				ResourceLocation resLoc = Utils.resourceLocationFromString(additionalTags.getString("id"));
				item = ForgeRegistries.ITEMS.getValue(resLoc);
			}
			if (item == null) { item = Items.AIR; }
			return new ItemParticleOption(particleType, new ItemStack(item));
		}
		return null;
	}

	public CompoundTag save(CompoundTag nbt)
	{
		if (particle != null)
		{
			ResourceLocation resourceLocation = ForgeRegistries.PARTICLE_TYPES.getKey(particle.getType());
			if (resourceLocation != null) { nbt.putString("id", resourceLocation.toString()); }
		}
		nbt.put("Motion", Utils.newDoubleListNBT(motion.x, motion.y, motion.z));
		nbt.put("MotionRand", Utils.newDoubleListNBT(motionRand.x, motionRand.y, motionRand.z));
		nbt.put("PositionOffset", Utils.newDoubleListNBT(posOffset.x, posOffset.y, posOffset.z));
		nbt.put("PositionRand", Utils.newDoubleListNBT(posRand.x, posRand.y, posRand.z));
		nbt.putInt("Interval", interval);
		nbt.putDouble("Probability", probability);
		nbt.putInt("ParticleCount", particleCount);
		nbt.putInt("ParticleMaxCount", particleMaxCount);
		if (additionalTags != null) { nbt.put("AdditionalTags", additionalTags); }
		return nbt;
	}

	public void renderParticle(Level world, Random random, double x, double y, double z)
	{
		if (particle == null) { return; }
		if (intervalCounter == interval)
		{
			if (random.nextDouble() <= probability)
			{
				int multiplier;
				if (particleCount >= particleMaxCount) { multiplier = particleCount; }
				else { multiplier = particleCount + random.nextInt(particleMaxCount - particleCount + 1); }
				for (int i = 0; i < multiplier; i++)
				{
					if (useRand)
					{
						double posX = x + posOffset.x + (random.nextDouble() - 0.5) * posRand.x;
						double posY = y + posOffset.y + (random.nextDouble() - 0.5) * posRand.y;
						double posZ = z + posOffset.z + (random.nextDouble() - 0.5) * posRand.z;
						double motionX = motion.x + (random.nextDouble() - 0.5) * motionRand.x;
						double motionY = motion.y + (random.nextDouble() - 0.5) * motionRand.y;
						double motionZ = motion.z + (random.nextDouble() - 0.5) * motionRand.z;
						world.addParticle(particle, posX, posY, posZ, motionX, motionY, motionZ);
					}
					else
					{
						double posX = x + posOffset.x;
						double posY = y + posOffset.y;
						double posZ = z + posOffset.z;
						world.addParticle(particle, posX, posY, posZ, motion.x, motion.y, motion.z);
					}
				}
			}
			intervalCounter = 0;
		}
		intervalCounter++;
	}
}
