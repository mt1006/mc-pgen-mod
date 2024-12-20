package com.mt1006.pgen.pgen;

import com.mojang.serialization.MapCodec;
import com.mt1006.pgen.PgenMod;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ParticleGeneratorBlock extends BaseEntityBlock
{
	public static final MapCodec<ParticleGeneratorBlock> CODEC = simpleCodec(ParticleGeneratorBlock::new);
	public static final EnumProperty<ParticlesPosition> PARTICLES_POSITION = EnumProperty.create("position", ParticlesPosition.class);
	public static boolean showShape = false;

	public ParticleGeneratorBlock(BlockBehaviour.Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PARTICLES_POSITION, ParticlesPosition.CENTER));
	}

	@Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(PARTICLES_POSITION);
	}

	@Override public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
	{
		return new ParticleGeneratorBlockEntity(blockPos, blockState);
	}

	@Override public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockReader,
												  @NotNull BlockPos blockPos, @NotNull CollisionContext ctx)
	{
		return showShape ? super.getShape(blockState, blockReader, blockPos, ctx) : Block.box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	}

	@Override public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockReader, @NotNull BlockPos blockPos)
	{
		return true;
	}

	@Override public void animateTick(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull RandomSource random)
	{
		BlockEntity blockEntity = level.getBlockEntity(blockPos);
		if (blockEntity instanceof ParticleGeneratorBlockEntity)
		{
			if(((ParticleGeneratorBlockEntity)blockEntity).useAnimateTick)
			{
				((ParticleGeneratorBlockEntity)blockEntity).renderParticles();
			}
		}
	}

	@Override @Nullable public <T extends BlockEntity> BlockEntityTicker<T>
			getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType)
	{
		return level.isClientSide
				? createTickerHelper(blockEntityType, PgenMod.loaderInterface.getBlockEntity(), ParticleGeneratorBlockEntity::tick)
				: null;
	}

	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A>
			createTickerHelper(BlockEntityType<A> a, BlockEntityType<E> b, BlockEntityTicker<? super E> c)
	{
		return a == b ? (BlockEntityTicker<A>)c : null;
	}

	@Override protected @NotNull MapCodec<? extends BaseEntityBlock> codec()
	{
		return CODEC;
	}
}
