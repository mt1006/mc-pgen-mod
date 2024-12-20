package com.mt1006.pgen.neoforge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegistryHandler
{
	private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PgenMod.MOD_ID);
	private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PgenMod.MOD_ID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PgenMod.MOD_ID);
	private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, PgenMod.MOD_ID);

	public static final Supplier<ParticleGeneratorBlock> BLOCK_PG = BLOCKS.registerBlock("particle_generator",
			ParticleGeneratorBlock::new, BlockBehaviour.Properties.of().strength(-1.0f, 3600000.8f)
					.noLootTable().noOcclusion().noCollission().pushReaction(PushReaction.BLOCK));

	public static final Supplier<BlockItem> ITEM_PG = ITEMS.registerItem("particle_generator",
			(props) -> new BlockItem(BLOCK_PG.get(), props.rarity(Rarity.EPIC).useBlockDescriptionPrefix()));

	public static final Supplier<BlockEntityType<ParticleGeneratorBlockEntity>> BLOCK_ENTITY_PG = BLOCK_ENTITY_TYPES.register("particle_generator",
			() -> new BlockEntityType<>(ParticleGeneratorBlockEntity::new, BLOCK_PG.get()));

	public static final Supplier<SimpleParticleType> PARTICLE_LOCATE = PARTICLE_TYPES.register("locate",
			() -> new SimpleParticleType(true));

	public static void register(IEventBus eventBus)
	{
		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		BLOCK_ENTITY_TYPES.register(eventBus);
		PARTICLE_TYPES.register(eventBus);
	}
}
