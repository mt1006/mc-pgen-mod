package com.mt1006.ParticleGenerator.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Random;

public class GeneratedParticleInfo
{
	private IParticleData particle = null;
	private Vector3d motion = Vector3d.ZERO;
	private Vector3d motionRand = Vector3d.ZERO;
	private Vector3d posOffset = Vector3d.ZERO;
	private Vector3d posRand = Vector3d.ZERO;
	private int interval = 1;
	private double probability = 1.0;
	private int particleCount = 1;
	private int particleMaxCount = 1;
	private CompoundNBT additionalTags = null;
	private boolean useRand = false;
	private int intervalCounter = 0;
	private final ParticleType[] BLOCK_PARTICLES = {ParticleTypes.BLOCK, ParticleTypes.FALLING_DUST};
	private final ParticleType[] ITEM_PARTICLES = {ParticleTypes.ITEM};

	public GeneratedParticleInfo(CompoundNBT nbt)
	{
		load(nbt);
	}

	public void load(CompoundNBT nbt)
	{
		if (nbt.contains("id"))
		{
			ResourceLocation resLoc = Utils.resourceLocationFromString(nbt.getString("id"));
			ParticleType particleType = ForgeRegistries.PARTICLE_TYPES.getValue(resLoc);
			if (particleType != null)
			{
				if (particleType instanceof IParticleData) { particle = (IParticleData)particleType; }
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
		if (!motionRand.equals(Vector3d.ZERO) || !posRand.equals(Vector3d.ZERO)) { useRand = true; }
	}

	private IParticleData loadComplexParticle(ParticleType particleType, CompoundNBT nbt)
	{
		CompoundNBT additionalTags = null;
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
			return new BlockParticleData(particleType, block.defaultBlockState());
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
			return new ItemParticleData(particleType, new ItemStack(item));
		}
		return null;
	}

	public CompoundNBT save(CompoundNBT nbt)
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

	public void renderParticle(World world, Random random, double x, double y, double z)
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
