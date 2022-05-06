package com.mt1006.ParticleGenerator.tileentity;

import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.block.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.state.ParticlesPosition;
import com.mt1006.ParticleGenerator.util.GeneratedParticleInfo;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ParticleGeneratorTileEntity extends TileEntity implements ITickableTileEntity
{
	private GeneratedParticleInfo[] particles = null;
	public boolean useAnimateTick = false;

	public ParticleGeneratorTileEntity()
	{
		this(RegistryHandler.TILE_ENTITY_PG.get());
	}

	public ParticleGeneratorTileEntity(TileEntityType<?> tileEntityType)
	{
		super(tileEntityType);
	}

	@Override
	public void load(BlockState blockState, CompoundNBT nbt)
	{
		super.load(blockState, nbt);
		if (nbt.contains("Particles"))
		{
			ListNBT particlesList = nbt.getList("Particles", 10); // 10 - CompoundNBT
			particles = new GeneratedParticleInfo[particlesList.size()];
			for (int i = 0; i < particlesList.size(); i++)
			{
				particles[i] = new GeneratedParticleInfo(particlesList.getCompound(i));
			}
		}
		if (nbt.contains("UseAnimateTick")) { useAnimateTick = nbt.getBoolean("UseAnimateTick"); }
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt)
	{
		super.save(nbt);
		if (particles != null)
		{
			ListNBT particlesList = new ListNBT();
			for (GeneratedParticleInfo particle : particles)
			{
				particlesList.add(particle.save(new CompoundNBT()));
			}
			nbt.put("Particles", particlesList);
		}
		nbt.putBoolean("UseAnimateTick", useAnimateTick);
		return nbt;
	}

	@Override
	public CompoundNBT getUpdateTag()
	{
		return save(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(BlockState blockState, CompoundNBT nbt)
	{
		this.load(blockState, nbt);
	}

	@Nullable @Override
	public SUpdateTileEntityPacket getUpdatePacket()
	{
		return new SUpdateTileEntityPacket(getBlockPos(), getType().hashCode(), save(new CompoundNBT()));
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet)
	{
		load(getBlockState(), packet.getTag());
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
		World world = getLevel();
		for (GeneratedParticleInfo particle : particles)
		{
			particle.renderParticle(world, world.random, pos.x, pos.y, pos.z);
		}
	}
}
