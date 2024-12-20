package com.mt1006.pgen;

import com.mt1006.pgen.network.PgenPacketS2C;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface PgenModLoaderInterface
{
	String getLoaderName();
	String getModVersion();

	Block getBlock();
	BlockEntityType<ParticleGeneratorBlockEntity> getBlockEntity();

	void sendPacketToClient(ServerPlayer player, PgenPacketS2C packet);
}
