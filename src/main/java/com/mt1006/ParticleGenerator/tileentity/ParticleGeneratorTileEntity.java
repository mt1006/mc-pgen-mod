package com.mt1006.ParticleGenerator.tileentity;

import com.mojang.math.Vector3d;
import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.block.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.state.ParticlesPosition;
import com.mt1006.ParticleGenerator.util.GeneratedParticleInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ParticleGeneratorTileEntity extends BlockEntity
{
	private GeneratedParticleInfo[] particles = null;
	public boolean useAnimateTick = false;

	public ParticleGeneratorTileEntity(BlockPos blockPos, BlockState blockState)
	{
		super(RegistryHandler.TILE_ENTITY_PG.get(), blockPos, blockState);
	}

	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
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
	public void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
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
	}

	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag nbt = new CompoundTag();
		saveAdditional(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(CompoundTag nbt)
	{
		this.load(nbt);
	}

	@Nullable @Override
	public Packet<ClientGamePacketListener> getUpdatePacket()
	{
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public static void tick(Level world, BlockPos blockPos, BlockState blockState, ParticleGeneratorTileEntity tileEntity)
	{
		if (tileEntity.useAnimateTick) { return; }
		tileEntity.renderParticles();
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
