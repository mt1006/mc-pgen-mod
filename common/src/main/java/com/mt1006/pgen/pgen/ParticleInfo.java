package com.mt1006.pgen.pgen;

import com.mojang.datafixers.util.Pair;
import com.mt1006.pgen.utils.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ParticleInfo
{
	private static final ParticleType<?>[] BLOCK_PARTICLES = {ParticleTypes.BLOCK, ParticleTypes.BLOCK_MARKER, ParticleTypes.FALLING_DUST};
	private static final ParticleType<?>[] ITEM_PARTICLES = {ParticleTypes.ITEM};

	private final ParticleOptions particle;
	private final Vec3 motion;
	private final Vec3 motionRand;
	private final Vec3 posOffset;
	private final Vec3 posRand;
	private final int interval;
	private final double probability;
	private final int particleCount;
	private final int particleMaxCount;
	private final boolean useRand;
	private CompoundTag additionalTags = null;
	private int intervalCounter = 0;

	public ParticleInfo(CompoundTag nbt)
	{
		particle = loadParticleType(nbt);
		motion = Utils.vector3dFromNBT(nbt, "Motion", Vec3.ZERO);
		motionRand = Utils.vector3dFromNBT(nbt, "MotionRand", Vec3.ZERO);
		posOffset = Utils.vector3dFromNBT(nbt, "PositionOffset", Vec3.ZERO);
		posRand = Utils.vector3dFromNBT(nbt, "PositionRand", Vec3.ZERO);
		interval = nbt.getIntOr("Interval", 1);
		probability = nbt.getDoubleOr("Probability", 1.0);
		particleCount = nbt.getIntOr("ParticleCount", 1);
		particleMaxCount = nbt.getIntOr("ParticleMaxCount", 1);
		useRand = (!motionRand.equals(Vec3.ZERO) || !posRand.equals(Vec3.ZERO));
	}

	public @Nullable ParticleOptions loadParticleType(CompoundTag nbt)
	{
		String particleId = nbt.getString("id").orElse(null);
		if (particleId == null) { return null; }

		ResourceLocation resLoc = Utils.resourceLocationFromString(particleId);
		Holder.Reference<ParticleType<?>> ref = BuiltInRegistries.PARTICLE_TYPE.get(resLoc).orElse(null);
		ParticleType<?> particleType = ref != null ? ref.value() : null;
		if (particleType == null) { return null; }

		if (particleType instanceof ParticleOptions)
		{
			return (ParticleOptions)particleType;
		}
		else
		{
			Pair<ParticleOptions, CompoundTag> pair = loadComplexParticle(particleType, nbt);
			if (pair == null) { return null; }
			additionalTags = pair.getSecond();
			return pair.getFirst();
		}
	}

	private static @Nullable Pair<ParticleOptions, CompoundTag> loadComplexParticle(ParticleType particleType, CompoundTag nbt)
	{
		CompoundTag additionalTags = nbt.getCompound("AdditionalTags").orElse(null);
		String additionalId = additionalTags != null ? additionalTags.getString("id").orElse(null) : null;
		ResourceLocation resLoc = additionalId != null ? Utils.resourceLocationFromString(additionalId) : null;

		if (Arrays.asList(BLOCK_PARTICLES).contains(particleType))
		{
			Block block = null;
			if (resLoc != null)
			{
				Holder.Reference<Block> ref = BuiltInRegistries.BLOCK.get(resLoc).orElse(null);
				block = ref != null ? ref.value() : null;
			}
			if (block == null) { block = Blocks.AIR; }
			return Pair.of(new BlockParticleOption(particleType, block.defaultBlockState()), additionalTags);
		}
		else if (Arrays.asList(ITEM_PARTICLES).contains(particleType))
		{
			Item item = null;
			if (resLoc != null)
			{
				Holder.Reference<Item> ref = BuiltInRegistries.ITEM.get(resLoc).orElse(null);
				item = ref != null ? ref.value() : null;
			}
			if (item == null) { item = Items.AIR; }
			return Pair.of(new ItemParticleOption(particleType, new ItemStack(item)), additionalTags);
		}
		return null;
	}

	public CompoundTag save(CompoundTag nbt)
	{
		if (particle != null)
		{
			ResourceLocation resourceLocation = BuiltInRegistries.PARTICLE_TYPE.getKey(particle.getType());
			if (resourceLocation != null) { nbt.putString("id", resourceLocation.toString()); }
		}
		nbt.put("Motion", Utils.doubleListToNBT(motion.x, motion.y, motion.z));
		nbt.put("MotionRand", Utils.doubleListToNBT(motionRand.x, motionRand.y, motionRand.z));
		nbt.put("PositionOffset", Utils.doubleListToNBT(posOffset.x, posOffset.y, posOffset.z));
		nbt.put("PositionRand", Utils.doubleListToNBT(posRand.x, posRand.y, posRand.z));
		nbt.putInt("Interval", interval);
		nbt.putDouble("Probability", probability);
		nbt.putInt("ParticleCount", particleCount);
		nbt.putInt("ParticleMaxCount", particleMaxCount);
		if (additionalTags != null) { nbt.put("AdditionalTags", additionalTags); }
		return nbt;
	}

	public void renderParticle(Level level, RandomSource random, double x, double y, double z)
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
						level.addParticle(particle, posX, posY, posZ, motionX, motionY, motionZ);
					}
					else
					{
						double posX = x + posOffset.x;
						double posY = y + posOffset.y;
						double posZ = z + posOffset.z;
						level.addParticle(particle, posX, posY, posZ, motion.x, motion.y, motion.z);
					}
				}
			}
			intervalCounter = 0;
		}
		intervalCounter++;
	}
}
