package com.mt1006.pgen.forge;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler
{
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PgenMod.MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PgenMod.MOD_ID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PgenMod.MOD_ID);

	private static final ResourceKey<Block> BLOCK_ID = ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(PgenMod.MOD_ID, "particle_generator"));
	private static final ResourceKey<Item> ITEM_ID = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PgenMod.MOD_ID, "particle_generator"));

	public static final RegistryObject<Block> BLOCK_PG = BLOCKS.register("particle_generator",
			() -> new ParticleGeneratorBlock(BlockBehaviour.Properties.of().strength(-1.0f, 3600000.8f)
					.noLootTable().noOcclusion().noCollission().pushReaction(PushReaction.BLOCK)));

	public static final RegistryObject<Item> ITEM_PG = ITEMS.register("particle_generator",
			() -> new BlockItem(BLOCK_PG.get(), new Item.Properties().rarity(Rarity.EPIC)));

	public static final RegistryObject<BlockEntityType<ParticleGeneratorBlockEntity>> BLOCK_ENTITY_PG = BLOCK_ENTITY_TYPES.register("particle_generator",
			() -> BlockEntityType.Builder.of(ParticleGeneratorBlockEntity::new, BLOCK_PG.get()).build(null));

	public static void register(IEventBus eventBus)
	{
		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		BLOCK_ENTITY_TYPES.register(eventBus);
	}
}
