package com.mt1006.ParticleGenerator.pgen.blockentity;

import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.pgen.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.pgen.ParticleInfo;
import com.mt1006.ParticleGenerator.pgen.blockstate.ParticlesPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParticleGeneratorBlockEntity extends BlockEntity
{
	private ParticleInfo[] particles = null;
	public boolean useAnimateTick = false;

	public ParticleGeneratorBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(RegistryHandler.TILE_ENTITY_PG.get(), blockPos, blockState);
	}

	@Override public void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookup)
	{
		super.loadAdditional(nbt, lookup);
		if (nbt.contains("Particles"))
		{
			ListTag particlesList = nbt.getList("Particles", Tag.TAG_COMPOUND);
			particles = new ParticleInfo[particlesList.size()];
			for (int i = 0; i < particlesList.size(); i++)
			{
				particles[i] = new ParticleInfo(particlesList.getCompound(i));
			}
		}
		if (nbt.contains("UseAnimateTick")) { useAnimateTick = nbt.getBoolean("UseAnimateTick"); }
	}

	@Override public void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookup)
	{
		super.saveAdditional(nbt, lookup);
		if (particles != null)
		{
			ListTag particlesList = new ListTag();
			for (ParticleInfo particle : particles)
			{
				particlesList.add(particle.save(new CompoundTag()));
			}
			nbt.put("Particles", particlesList);
		}
		nbt.putBoolean("UseAnimateTick", useAnimateTick);
	}

	@Override public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookup)
	{
		CompoundTag nbt = new CompoundTag();
		saveAdditional(nbt, lookup);
		return nbt;
	}

	@Override public @Nullable Packet<ClientGamePacketListener> getUpdatePacket()
	{
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, ParticleGeneratorBlockEntity blockEntity)
	{
		if (blockEntity.useAnimateTick) { return; }
		blockEntity.renderParticles();
	}

	public void renderParticles()
	{
		if (particles == null) { return; }
		ParticlesPosition position = getBlockState().getValue(ParticleGeneratorBlock.PARTICLES_POSITION);
		Vec3 pos = position.getFinalPosition(getBlockPos());
		Level level = getLevel();
		if (level == null) { return; }

		for (ParticleInfo particle : particles)
		{
			particle.renderParticle(level, level.random, pos.x, pos.y, pos.z);
		}
	}
}
