package com.mt1006.pgen;

import com.mt1006.pgen.fabric.RegisterCommands;
import com.mt1006.pgen.fabric.RegistryHandler;
import com.mt1006.pgen.network.PgenPacketS2C;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;

public class PgenModFabric implements ModInitializer, PgenModLoaderInterface
{
	private static final FabricLoader FABRIC_LOADER = FabricLoader.getInstance();
	public static final boolean isDedicatedServer = FABRIC_LOADER.getEnvironmentType() == EnvType.SERVER;

	@Override public void onInitialize()
	{
		PgenMod.init(isDedicatedServer, this);

		RegistryHandler.register();
		RegisterCommands.registerCommands();
	}

	@Override public String getLoaderName()
	{
		return "Fabric";
	}

	@Override public String getModVersion()
	{
		Optional<ModContainer> modContainer = FABRIC_LOADER.getModContainer(PgenMod.MOD_ID);
		return modContainer.isPresent() ? modContainer.get().getMetadata().getVersion().getFriendlyString() : "[unknown]";
	}

	@Override public Block getBlock()
	{
		return RegistryHandler.BLOCK_PG;
	}

	@Override public BlockEntityType<ParticleGeneratorBlockEntity> getBlockEntity()
	{
		return RegistryHandler.BLOCK_ENTITY_PG;
	}

	@Override public SimpleParticleType getParticle()
	{
		return RegistryHandler.PARTICLE_LOCATE;
	}

	@Override public void sendPacketToClient(ServerPlayer player, PgenPacketS2C packet)
	{
		ServerPlayNetworking.send(player, packet);
	}
}
