package com.mt1006.pgen.pgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ParticleInfo
{
	private static final ParticleType<?>[] BLOCK_PARTICLES = {ParticleTypes.BLOCK, ParticleTypes.BLOCK_MARKER,
			ParticleTypes.FALLING_DUST, ParticleTypes.DUST_PILLAR, ParticleTypes.BLOCK_CRUMBLE};
	private static final ParticleType<?>[] ITEM_PARTICLES = {ParticleTypes.ITEM};

	private final @Nullable ParticleOptions particle;
	private final Vec3 motion;
	private final Vec3 motionRand;
	private final Vec3 posOffset;
	private final Vec3 posRand;
	private final int interval;
	private final double probability;
	private final int particleCount;
	private final int particleMaxCount;
	private final boolean useRand;
	private final int signalMin;
	private final int signalMax;
	private @Nullable CompoundTag additionalTags = null;
	private int intervalCounter = 0;

	public ParticleInfo(CompoundTag nbt)
	{
		particle = loadParticleType(nbt);
		motion = vector3dFromNBT(nbt, "Motion", Vec3.ZERO);
		motionRand = vector3dFromNBT(nbt, "MotionRand", Vec3.ZERO);
		posOffset = vector3dFromNBT(nbt, "PositionOffset", Vec3.ZERO);
		posRand = vector3dFromNBT(nbt, "PositionRand", Vec3.ZERO);
		interval = getIntOr(nbt, "Interval", 1);
		probability = getDoubleOr(nbt, "Probability", 1.0);
		particleCount = getIntOr(nbt, "ParticleCount", 1);
		particleMaxCount = getIntOr(nbt, "ParticleMaxCount", 1);
		signalMin = getIntOr(nbt, "SignalMin", Integer.MIN_VALUE);
		signalMax = getIntOr(nbt, "SignalMax", Integer.MAX_VALUE);
		useRand = (!motionRand.equals(Vec3.ZERO) || !posRand.equals(Vec3.ZERO));
	}

	public @Nullable ParticleOptions loadParticleType(CompoundTag nbt)
	{
		String particleId = nbt.getString("id");
		if (particleId.isEmpty()) { return null; }

		ResourceLocation id = ResourceLocation.tryParse(particleId);
		if (id == null) { return null; }

		Holder.Reference<ParticleType<?>> ref = BuiltInRegistries.PARTICLE_TYPE.get(id).orElse(null);
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
		//TODO: use ParticleType codec instead of AdditionalTags

		CompoundTag additionalTags = nbt.getCompound("AdditionalTags");
		String idStr = additionalTags.getString("id");
		ResourceLocation id = idStr.isEmpty() ? null : ResourceLocation.tryParse(idStr);

		if (Arrays.asList(BLOCK_PARTICLES).contains(particleType))
		{
			Block block = null;
			if (id != null)
			{
				Holder.Reference<Block> ref = BuiltInRegistries.BLOCK.get(id).orElse(null);
				block = ref != null ? ref.value() : null;
			}
			return block != null ? Pair.of(new BlockParticleOption(particleType, block.defaultBlockState()), additionalTags) : null;
		}
		else if (Arrays.asList(ITEM_PARTICLES).contains(particleType))
		{
			Item item = null;
			if (id != null)
			{
				Holder.Reference<Item> ref = BuiltInRegistries.ITEM.get(id).orElse(null);
				item = ref != null ? ref.value() : null;
			}
			return item != null ? Pair.of(new ItemParticleOption(particleType, new ItemStack(item)), additionalTags) : null;
		}
		return null;
	}

	public CompoundTag save(CompoundTag nbt)
	{
		if (particle != null)
		{
			ResourceLocation particleId = BuiltInRegistries.PARTICLE_TYPE.getKey(particle.getType());
			if (particleId != null) { nbt.putString("id", particleId.toString()); }
		}
		nbt.put("Motion", doubleListToNBT(motion.x, motion.y, motion.z));
		nbt.put("MotionRand", doubleListToNBT(motionRand.x, motionRand.y, motionRand.z));
		nbt.put("PositionOffset", doubleListToNBT(posOffset.x, posOffset.y, posOffset.z));
		nbt.put("PositionRand", doubleListToNBT(posRand.x, posRand.y, posRand.z));
		nbt.putInt("Interval", interval);
		nbt.putDouble("Probability", probability);
		nbt.putInt("ParticleCount", particleCount);
		nbt.putInt("ParticleMaxCount", particleMaxCount);
		nbt.putInt("SignalMin", signalMin);
		nbt.putInt("SignalMax", signalMax);
		if (additionalTags != null) { nbt.put("AdditionalTags", additionalTags); }
		return nbt;
	}

	public void renderParticle(Level level, RandomSource random, double x, double y, double z, int redstoneSignal)
	{
		if (particle == null) { return; }
		if (redstoneSignal < signalMin || redstoneSignal > signalMax)
		{
			intervalCounter = 0;
			return;
		}

		if (intervalCounter == interval)
		{
			if (random.nextDouble() <= probability)
			{
				int multiplier = (particleCount >= particleMaxCount)
						? particleCount
						: particleCount + random.nextInt(particleMaxCount - particleCount + 1);

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

	private static int getIntOr(CompoundTag nbt, String name, int defVal)
	{
		return nbt.contains(name) ? nbt.getInt(name) : defVal;
	}

	private static double getDoubleOr(CompoundTag nbt, String name, double defVal)
	{
		return nbt.contains(name) ? nbt.getDouble(name) : defVal;
	}

	private static ListTag doubleListToNBT(double... input)
	{
		ListTag list = new ListTag();
		for (double val : input)
		{
			list.add(DoubleTag.valueOf(val));
		}
		return list;
	}

	private static Vec3 vector3dFromNBT(CompoundTag nbt, String name, Vec3 defVal)
	{
		if (!nbt.contains(name)) { return defVal; }

		ListTag motionList = nbt.getList(name, Tag.TAG_DOUBLE);
		double x = motionList.getDouble(0);
		double y = motionList.getDouble(1);
		double z = motionList.getDouble(2);
		return new Vec3(x, y, z);
	}
}
