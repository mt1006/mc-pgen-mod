package com.mt1006.ParticleGenerator.block;

import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.state.ParticlesPosition;
import com.mt1006.ParticleGenerator.tileentity.ParticleGeneratorTileEntity;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

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
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new ParticleGeneratorTileEntity(blockPos, blockState);
	}

	@Override @Deprecated
	public VoxelShape getShape(BlockState blockState, BlockGetter blockReader,
							   BlockPos blockPos, CollisionContext ctx)
	{
		if (showShape)
		{
			return super.getShape(blockState, blockReader, blockPos, ctx);
		}
		else
		{
			return Block.box(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockReader, BlockPos blockPos)
	{
		return true;
	}

	@Override @OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState blockState, Level world, BlockPos blockPos, Random random)
	{
		BlockEntity tileEntity = world.getBlockEntity(blockPos);
		if (tileEntity instanceof ParticleGeneratorTileEntity)
		{
			if(((ParticleGeneratorTileEntity)tileEntity).useAnimateTick)
			{
				((ParticleGeneratorTileEntity)tileEntity).renderParticles();
			}
		}
		if (locateCounter > 0)
		{
			world.addParticle(RegistryHandler.PARTICLE_LOCATE.get(),
					blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);
			locateCounter--;
		}
	}

	@Override @Nullable
	public <T extends BlockEntity> BlockEntityTicker<T>
			getTicker(Level world, BlockState blockState, BlockEntityType<T> blockEntityType)
	{
		if (world.isClientSide)
		{
			return createTickerHelper(blockEntityType, RegistryHandler.TILE_ENTITY_PG.get(),
					ParticleGeneratorTileEntity::tick);
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
