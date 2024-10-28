package com.mt1006.pgen;

import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import com.mt1006.pgen.pgen.blockentity.ParticleGeneratorBlockEntity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegistryHandler
{
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PgenMod.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(PgenMod.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, PgenMod.MOD_ID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, PgenMod.MOD_ID);

	public static final DeferredHolder<Block, ParticleGeneratorBlock> BLOCK_PG = BLOCKS.registerBlock("particle_generator",
			ParticleGeneratorBlock::new, BlockBehaviour.Properties.of().strength(-1.0f, 3600000.8f).noLootTable().noOcclusion().noCollission().pushReaction(PushReaction.BLOCK));

	public static final DeferredHolder<Item, BlockItem> ITEM_PG = ITEMS.registerItem("particle_generator",
			(props) -> new BlockItem(BLOCK_PG.get(), props.rarity(Rarity.EPIC).useBlockDescriptionPrefix()));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ParticleGeneratorBlockEntity>> TILE_ENTITY_PG = BLOCK_ENTITY_TYPES.register("particle_generator",
			() -> new BlockEntityType<>(ParticleGeneratorBlockEntity::new, BLOCK_PG.get()));

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PARTICLE_LOCATE = PARTICLE_TYPES.register("locate",
			() -> new SimpleParticleType(true));

	public static void register(IEventBus eventBus)
	{
		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		BLOCK_ENTITY_TYPES.register(eventBus);
		PARTICLE_TYPES.register(eventBus);
	}
}
