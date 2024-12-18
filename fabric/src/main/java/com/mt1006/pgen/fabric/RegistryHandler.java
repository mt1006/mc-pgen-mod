package com.mt1006.pgen.fabric;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import com.mt1006.pgen.pgen.blockentity.ParticleGeneratorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
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

public class RegistryHandler
{
	private static final ResourceLocation PG_ID = ResourceLocation.fromNamespaceAndPath(PgenMod.MOD_ID, "particle_generator");

	public static final Block BLOCK_PG =
			new ParticleGeneratorBlock(BlockBehaviour.Properties.of().strength(-1.0f, 3600000.8f).noLootTable().noOcclusion()
					.noCollission().pushReaction(PushReaction.BLOCK).setId(ResourceKey.create(Registries.BLOCK, PG_ID)));

	public static final Item ITEM_PG =
			new BlockItem(BLOCK_PG, new Item.Properties().rarity(Rarity.EPIC)
					.useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, PG_ID)));

	public static final BlockEntityType<ParticleGeneratorBlockEntity> BLOCK_ENTITY_PG =
			FabricBlockEntityTypeBuilder.create(ParticleGeneratorBlockEntity::new, BLOCK_PG).build();

	public static final SimpleParticleType PARTICLE_LOCATE = FabricParticleTypes.simple(true);

	public static void register()
	{
		Registry.register(BuiltInRegistries.BLOCK, PG_ID, BLOCK_PG);
		Registry.register(BuiltInRegistries.ITEM, PG_ID, ITEM_PG);
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, PG_ID, BLOCK_ENTITY_PG);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(PgenMod.MOD_ID, "locate"), PARTICLE_LOCATE);
	}
}
