package com.mt1006.pgen.pgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
		useRand = (!motionRand.equals(Vec3.ZERO) || !posRand.equals(Vec3.ZERO));
	}

	public @Nullable ParticleOptions loadParticleType(ValueInput nbt)
	{
		String particleId = nbt.getString("id").orElse(null);
		if (particleId == null) { return null; }

		ResourceLocation id = ResourceLocation.tryParse(particleId);
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
		ValueInput additionalTags = nbt.child("AdditionalTags").orElse(null);
		String additionalId = additionalTags != null ? additionalTags.getString("id").orElse(null) : null;
		ResourceLocation id = additionalId != null ? ResourceLocation.tryParse(additionalId) : null;

		if (Arrays.asList(BLOCK_PARTICLES).contains(particleType))
		{
			Block block = null;
			if (id != null)
			{
				Holder.Reference<Block> ref = BuiltInRegistries.BLOCK.get(id).orElse(null);
				block = ref != null ? ref.value() : null;
			}
			if (block == null) { block = Blocks.AIR; }
			return Pair.of(new BlockParticleOption(particleType, block.defaultBlockState()), additionalId);
		}
		else if (Arrays.asList(ITEM_PARTICLES).contains(particleType))
		{
			Item item = null;
			if (id != null)
			{
				Holder.Reference<Item> ref = BuiltInRegistries.ITEM.get(id).orElse(null);
				item = ref != null ? ref.value() : null;
			}
			if (item == null) { item = Items.AIR; }
			return Pair.of(new ItemParticleOption(particleType, new ItemStack(item)), additionalId);
		}
		return null;
	}

	public ValueOutput save(ValueOutput nbt)
	{
		if (particle != null)
		{
			ResourceLocation resourceLocation = BuiltInRegistries.PARTICLE_TYPE.getKey(particle.getType());
			if (resourceLocation != null) { nbt.putString("id", resourceLocation.toString()); }
		}
		nbt.store("Motion", Vec3.CODEC, motion);
		nbt.store("MotionRand", Vec3.CODEC, motionRand);
		nbt.store("PositionOffset", Vec3.CODEC, posOffset);
		nbt.store("PositionRand", Vec3.CODEC, posRand);
		nbt.putInt("Interval", interval);
		nbt.putDouble("Probability", probability);
		nbt.putInt("ParticleCount", particleCount);
		nbt.putInt("ParticleMaxCount", particleMaxCount);
		if (additionalId != null) { nbt.child("AdditionalTags").putString("id", additionalId); }
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
