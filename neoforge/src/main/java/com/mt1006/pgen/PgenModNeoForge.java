package com.mt1006.pgen;

import com.mt1006.pgen.neoforge.RegistryHandler;
import com.mt1006.pgen.network.PgenPacketS2C;
import com.mt1006.pgen.pgen.blockentity.ParticleGeneratorBlockEntity;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

@Mod(PgenMod.MOD_ID)
public class PgenModNeoForge implements PgenModLoaderInterface
{
	public static final boolean isDedicatedServer = FMLEnvironment.dist.isDedicatedServer();
	private final @Nullable ModContainer modContainer;

	public PgenModNeoForge(IEventBus eventBus)
	{
		ModContainer modContainer = ModLoadingContext.get().getActiveContainer();
		this.modContainer = modContainer.getModId().equals("minecraft") ? null : modContainer;
		PgenMod.init(isDedicatedServer, this);

		RegistryHandler.register(eventBus);
	}

	@Override public String getLoaderName()
	{
		return "NeoForge";
	}

	@Override public String getModVersion()
	{
		return modContainer != null ? modContainer.getModInfo().getVersion().toString() : "[unknown]";
	}

	@Override public Block getBlock()
	{
		return RegistryHandler.BLOCK_PG.get();
	}

	@Override public BlockEntityType<ParticleGeneratorBlockEntity> getBlockEntity()
	{
		return RegistryHandler.BLOCK_ENTITY_PG.get();
	}

	@Override public SimpleParticleType getParticle()
	{
		return RegistryHandler.PARTICLE_LOCATE.get();
	}

	@Override public void sendPacketToClient(ServerPlayer player, PgenPacketS2C packet)
	{
		PacketDistributor.sendToPlayer(player, packet);
	}
}
