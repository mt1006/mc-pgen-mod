package com.mt1006.pgen.fabric;

import com.mt1006.pgen.PgenMod;
import com.mt1006.pgen.pgen.ParticleGeneratorBlock;
import com.mt1006.pgen.pgen.ParticleGeneratorBlockEntity;
import net.minecraft.core.Registry;
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
	private static final ResourceLocation PG_ID = new ResourceLocation(PgenMod.MOD_ID, "particle_generator");

	public static final Block BLOCK_PG =
			new ParticleGeneratorBlock(BlockBehaviour.Properties.of().strength(-1.0f, 3600000.8f)
					.noLootTable().noOcclusion().noCollission().pushReaction(PushReaction.BLOCK));

	public static final Item ITEM_PG = new BlockItem(BLOCK_PG, new Item.Properties().rarity(Rarity.EPIC));

	public static final BlockEntityType<ParticleGeneratorBlockEntity> BLOCK_ENTITY_PG =
			BlockEntityType.Builder.of(ParticleGeneratorBlockEntity::new, BLOCK_PG).build(null);

	public static void register()
	{
		Registry.register(BuiltInRegistries.BLOCK, PG_ID, BLOCK_PG);
		Registry.register(BuiltInRegistries.ITEM, PG_ID, ITEM_PG);
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, PG_ID, BLOCK_ENTITY_PG);
	}
}
