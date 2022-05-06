package com.mt1006.ParticleGenerator.tileentity;

import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.block.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.state.ParticlesPosition;
import com.mt1006.ParticleGenerator.util.GeneratedParticleInfo;
import com.mt1006.ParticleGenerator.util.Vector3d;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ParticleGeneratorTileEntity extends BlockEntity implements TickableBlockEntity, BlockEntityClientSerializable
{
	private GeneratedParticleInfo[] particles = null;
	public boolean useAnimateTick = false;

	public ParticleGeneratorTileEntity()
	{
		this(RegistryHandler.TILE_ENTITY_PG);
	}

	public ParticleGeneratorTileEntity(BlockEntityType<?> tileEntityType)
	{
		super(tileEntityType);
	}

	@Override
	public void load(BlockState blockState, CompoundTag nbt)
	{
		super.load(blockState, nbt);
		if (nbt.contains("Particles"))
		{
			ListTag particlesList = nbt.getList("Particles", 10); // 10 - CompoundNBT / CompoundTag
			particles = new GeneratedParticleInfo[particlesList.size()];
			for (int i = 0; i < particlesList.size(); i++)
			{
				particles[i] = new GeneratedParticleInfo(particlesList.getCompound(i));
			}
		}
		if (nbt.contains("UseAnimateTick")) { useAnimateTick = nbt.getBoolean("UseAnimateTick"); }
	}

	@Override
	public CompoundTag save(CompoundTag nbt)
	{
		super.save(nbt);
		if (particles != null)
		{
			ListTag particlesList = new ListTag();
			for (GeneratedParticleInfo particle : particles)
			{
				particlesList.add(particle.save(new CompoundTag()));
			}
			nbt.put("Particles", particlesList);
		}
		nbt.putBoolean("UseAnimateTick", useAnimateTick);
		return nbt;
	}

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		load(null, tag);
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag)
	{
		return save(tag);
	}

	@Override
	public void tick()
	{
		if (useAnimateTick) { return; }
		renderParticles();
	}

	public void renderParticles()
	{
		if (particles == null) { return; }
		ParticlesPosition position = getBlockState().getValue(ParticleGeneratorBlock.PARTICLES_POSITION);
		Vector3d pos = position.getFinalPosition(getBlockPos());
		Level world = getLevel();
		for (GeneratedParticleInfo particle : particles)
		{
			particle.renderParticle(world, world.random, pos.x, pos.y, pos.z);
		}
	}
}
