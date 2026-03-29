package com.mt1006.pgen.pgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
	private @Nullable String additionalId = null;
	private int intervalCounter = 0;

	public ParticleInfo(ValueInput nbt)
	{
		particle = loadParticleType(nbt);
		motion = nbt.read("Motion", Vec3.CODEC).orElse(Vec3.ZERO);
		motionRand = nbt.read("MotionRand", Vec3.CODEC).orElse(Vec3.ZERO);
		posOffset = nbt.read("PositionOffset", Vec3.CODEC).orElse(Vec3.ZERO);
		posRand = nbt.read("PositionRand", Vec3.CODEC).orElse(Vec3.ZERO);
		interval = nbt.getIntOr("Interval", 1);
		probability = nbt.getDoubleOr("Probability", 1.0);
		particleCount = nbt.getIntOr("ParticleCount", 1);
		particleMaxCount = nbt.getIntOr("ParticleMaxCount", 1);
		signalMin = nbt.getIntOr("SignalMin", Integer.MIN_VALUE);
		signalMax = nbt.getIntOr("SignalMax", Integer.MAX_VALUE);
		useRand = (!motionRand.equals(Vec3.ZERO) || !posRand.equals(Vec3.ZERO));
	}

	public @Nullable ParticleOptions loadParticleType(ValueInput nbt)
	{
		String particleId = nbt.getString("id").orElse(null);
		if (particleId == null) { return null; }

		Identifier id = Identifier.tryParse(particleId);
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
			Pair<ParticleOptions, String> pair = loadComplexParticle(particleType, nbt);
			if (pair == null) { return null; }
			additionalId = pair.getSecond();
			return pair.getFirst();
		}
	}

	private static @Nullable Pair<ParticleOptions, String> loadComplexParticle(ParticleType particleType, ValueInput nbt)
	{
		//TODO: use ParticleType codec instead of AdditionalTags

		ValueInput additionalTags = nbt.child("AdditionalTags").orElse(null);
		String idStr = additionalTags != null ? additionalTags.getString("id").orElse(null) : null;
		Identifier id = idStr != null ? Identifier.tryParse(idStr) : null;

		if (Arrays.asList(BLOCK_PARTICLES).contains(particleType))
		{
			Block block = null;
			if (id != null)
			{
				Holder.Reference<Block> ref = BuiltInRegistries.BLOCK.get(id).orElse(null);
				block = ref != null ? ref.value() : null;
			}
			return block != null ? Pair.of(new BlockParticleOption(particleType, block.defaultBlockState()), idStr) : null;
		}
		else if (Arrays.asList(ITEM_PARTICLES).contains(particleType))
		{
			Item item = null;
			if (id != null)
			{
				Holder.Reference<Item> ref = BuiltInRegistries.ITEM.get(id).orElse(null);
				item = ref != null ? ref.value() : null;
			}
			return item != null ? Pair.of(new ItemParticleOption(particleType, new ItemStackTemplate(item)), idStr) : null;
		}
		return null;
	}

	public void save(ValueOutput nbt)
	{
		if (particle != null)
		{
			Identifier particleId = BuiltInRegistries.PARTICLE_TYPE.getKey(particle.getType());
			if (particleId != null) { nbt.putString("id", particleId.toString()); }
		}
		nbt.store("Motion", Vec3.CODEC, motion);
		nbt.store("MotionRand", Vec3.CODEC, motionRand);
		nbt.store("PositionOffset", Vec3.CODEC, posOffset);
		nbt.store("PositionRand", Vec3.CODEC, posRand);
		nbt.putInt("Interval", interval);
		nbt.putDouble("Probability", probability);
		nbt.putInt("ParticleCount", particleCount);
		nbt.putInt("ParticleMaxCount", particleMaxCount);
		nbt.putInt("SignalMin", signalMin);
		nbt.putInt("SignalMax", signalMax);
		if (additionalId != null) { nbt.child("AdditionalTags").putString("id", additionalId); }
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
}
