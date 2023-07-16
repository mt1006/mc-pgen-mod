package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.pgen.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.pgen.blockentity.ParticleGeneratorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class RegistryHandler
{
	public static final Block BLOCK_PG =
			new ParticleGeneratorBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noLootTable().noOcclusion().noCollission().pushReaction(PushReaction.BLOCK));

	public static final Item ITEM_PG =
			new BlockItem(BLOCK_PG, new Item.Properties().rarity(Rarity.EPIC));

	public static final BlockEntityType<ParticleGeneratorBlockEntity> TILE_ENTITY_PG =
			FabricBlockEntityTypeBuilder.create(ParticleGeneratorBlockEntity::new, BLOCK_PG).build(null);

	public static final SimpleParticleType PARTICLE_LOCATE = FabricParticleTypes.simple(true);

	public static void register()
	{
		Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(ParticleGenerator.MOD_ID, "particle_generator"), BLOCK_PG);
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ParticleGenerator.MOD_ID, "particle_generator"), ITEM_PG);
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(ParticleGenerator.MOD_ID, "particle_generator"), TILE_ENTITY_PG);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(ParticleGenerator.MOD_ID, "locate"), PARTICLE_LOCATE);
	}
}
