package com.mt1006.ParticleGenerator;

import com.mt1006.ParticleGenerator.block.ParticleGeneratorBlock;
import com.mt1006.ParticleGenerator.tileentity.ParticleGeneratorTileEntity;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class RegistryHandler
{
	public static final Block BLOCK_PG =
			new ParticleGeneratorBlock(BlockBehaviour.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion().noCollission());

	public static final Item ITEM_PG =
			new BlockItem(BLOCK_PG, new Item.Properties().rarity(Rarity.EPIC));

	public static final BlockEntityType<ParticleGeneratorTileEntity> TILE_ENTITY_PG =
			FabricBlockEntityTypeBuilder.create(ParticleGeneratorTileEntity::new, BLOCK_PG).build(null);

	public static final SimpleParticleType PARTICLE_LOCATE = FabricParticleTypes.simple(true);

	public static void register()
	{
		Registry.register(Registry.BLOCK, new ResourceLocation(ParticleGenerator.MOD_ID, "particle_generator"), BLOCK_PG);
		Registry.register(Registry.ITEM, new ResourceLocation(ParticleGenerator.MOD_ID, "particle_generator"), ITEM_PG);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(ParticleGenerator.MOD_ID, "particle_generator"), TILE_ENTITY_PG);
		Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(ParticleGenerator.MOD_ID, "locate"), PARTICLE_LOCATE);
	}
}
