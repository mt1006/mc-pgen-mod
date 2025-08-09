package com.mt1006.pgen.pgen;

import com.mt1006.pgen.PgenMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ParticleGeneratorBlockEntity extends BlockEntity
{
	private List<ParticleInfo> particles = null;
	public boolean useAnimateTick = false;

	public ParticleGeneratorBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(PgenMod.loaderInterface.getBlockEntity(), blockPos, blockState);
	}

	@Override public void loadAdditional(ValueInput nbt)
	{
		super.loadAdditional(nbt);

		ValueInput.ValueInputList particlesList = nbt.childrenListOrEmpty("Particles");
		particles = new ArrayList<>();
		particlesList.forEach((p) -> particles.add(new ParticleInfo(p)));

		useAnimateTick = nbt.getBooleanOr("UseAnimateTick", false);
	}

	@Override public void saveAdditional(ValueOutput nbt)
	{
		super.saveAdditional(nbt);
		if (particles != null)
		{
			ValueOutput.ValueOutputList outputList = nbt.childrenList("Particles");
			particles.forEach((p) -> p.save(outputList.addChild()));
		}
		nbt.putBoolean("UseAnimateTick", useAnimateTick);
	}

	@Override public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider lookup)
	{
		return saveCustomOnly(lookup);
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
		Level level = getLevel();
		if (particles == null || level == null) { return; }
		ParticlesPosition position = getBlockState().getValue(ParticleGeneratorBlock.PARTICLES_POSITION);
		Vec3 pos = position.getFinalPosition(getBlockPos());

		particles.forEach((p) -> p.renderParticle(level, level.random, pos.x, pos.y, pos.z));
	}
}
