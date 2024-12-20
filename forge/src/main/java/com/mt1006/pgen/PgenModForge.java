package com.mt1006.pgen;

import com.mt1006.pgen.forge.PacketHandler;
import com.mt1006.pgen.forge.RegistryHandler;
import com.mt1006.pgen.network.PgenPacketS2C;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

@Mod(PgenMod.MOD_ID)
public class PgenModForge implements PgenModLoaderInterface
{
	public static final boolean isDedicatedServer = FMLEnvironment.dist.isDedicatedServer();
	private final @Nullable ModContainer modContainer;

	public PgenModForge()
	{
		ModContainer modContainer = ModLoadingContext.get().getActiveContainer();
		this.modContainer = modContainer.getModId().equals("minecraft") ? null : modContainer;
		PgenMod.init(isDedicatedServer, this);

		RegistryHandler.register(FMLJavaModLoadingContext.get().getModEventBus());
		PacketHandler.register();
	}

	@Override public String getLoaderName()
	{
		return "Forge";
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

	@Override public void sendPacketToClient(ServerPlayer player, PgenPacketS2C packet)
	{
		PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
	}
}
