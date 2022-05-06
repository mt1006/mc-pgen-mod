package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.block.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.tileentity.ParticleGeneratorTileEntity;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ParticleGenerator.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ParticleGenerator.MOD_ID);
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ParticleGenerator.MOD_ID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ParticleGenerator.MOD_ID);

	public static final RegistryObject<Block> BLOCK_PG = BLOCKS.register("particle_generator",
			() -> new ParticleGeneratorBlock(BlockBehaviour.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion().noCollission()));

	public static final RegistryObject<Item> ITEM_PG = ITEMS.register("particle_generator",
			() -> new BlockItem(BLOCK_PG.get(), new Item.Properties().rarity(Rarity.EPIC)));

	public static final RegistryObject<BlockEntityType<ParticleGeneratorTileEntity>> TILE_ENTITY_PG = TILE_ENTITY_TYPES.register("particle_generator",
			() -> BlockEntityType.Builder.of(ParticleGeneratorTileEntity::new, BLOCK_PG.get()).build(null));

	public static final RegistryObject<SimpleParticleType> PARTICLE_LOCATE = PARTICLE_TYPES.register("locate",
			() -> new SimpleParticleType(true));

	public static void register()
	{
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(eventBus);
		ITEMS.register(eventBus);
		TILE_ENTITY_TYPES.register(eventBus);
		PARTICLE_TYPES.register(eventBus);
	}
}
