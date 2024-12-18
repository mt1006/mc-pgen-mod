package com.mt1006.pgen;

import com.mt1006.pgen.network.PgenPacketS2C;
import com.mt1006.pgen.pgen.blockentity.ParticleGeneratorBlockEntity;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface PgenModLoaderInterface
{
	String getLoaderName();
	String getModVersion();

	Block getBlock();
	BlockEntityType<ParticleGeneratorBlockEntity> getBlockEntity();
	SimpleParticleType getParticle();

	void sendPacketToClient(ServerPlayer player, PgenPacketS2C packet);
}
