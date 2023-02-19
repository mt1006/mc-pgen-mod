package com.mt1006.ParticleGenerator.pgen;

import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.pgen.blockstate.ParticlesPosition;
import com.mt1006.ParticleGenerator.pgen.blockentity.ParticleGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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

public class ParticleGeneratorBlock extends Block implements EntityBlock
{
	public static final EnumProperty<ParticlesPosition> PARTICLES_POSITION = EnumProperty.create("position", ParticlesPosition.class);
	public static boolean showShape = false;
	private static int locateCounter = 0;
	private static final int MAX_TO_LOCATE = 16;

	public ParticleGeneratorBlock(BlockBehaviour.Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PARTICLES_POSITION, ParticlesPosition.CENTER));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(PARTICLES_POSITION);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
	{
		return new ParticleGeneratorBlockEntity(blockPos, blockState);
	}

	@Override @Deprecated
	public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockReader,
										@NotNull BlockPos blockPos, @NotNull CollisionContext ctx)
	{
		return showShape ? super.getShape(blockState, blockReader, blockPos, ctx) : Block.box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockReader, @NotNull BlockPos blockPos)
	{
		return true;
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, Level world, @NotNull BlockPos blockPos, @NotNull RandomSource random)
	{
		BlockEntity tileEntity = world.getBlockEntity(blockPos);
		if (tileEntity instanceof ParticleGeneratorBlockEntity)
		{
			if(((ParticleGeneratorBlockEntity)tileEntity).useAnimateTick)
			{
				((ParticleGeneratorBlockEntity)tileEntity).renderParticles();
			}
		}
		if (locateCounter > 0)
		{
			world.addParticle(RegistryHandler.PARTICLE_LOCATE,
					blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);
			locateCounter--;
		}
	}

	@Override @Nullable
	public <T extends BlockEntity> BlockEntityTicker<T>
			getTicker(Level world, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType)
	{
		if (world.isClientSide)
		{
			return createTickerHelper(blockEntityType, RegistryHandler.TILE_ENTITY_PG, ParticleGeneratorBlockEntity::tick);
		}
		else { return null; }
	}

	protected static <E extends BlockEntity, A extends BlockEntity>BlockEntityTicker<A>
			createTickerHelper(BlockEntityType<A> a, BlockEntityType<E> b, BlockEntityTicker<? super E> c)
	{
		return a == b ? (BlockEntityTicker<A>)c : null;
	}

	public static void locate()
	{
		locateCounter = MAX_TO_LOCATE;
	}
}
