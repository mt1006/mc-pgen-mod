package com.mt1006.ParticleGenerator.block;

import com.mt1006.ParticleGenerator.RegistryHandler;
import com.mt1006.ParticleGenerator.state.ParticlesPosition;
import com.mt1006.ParticleGenerator.tileentity.ParticleGeneratorTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class ParticleGeneratorBlock extends Block
{
	public static final EnumProperty<ParticlesPosition> PARTICLES_POSITION = EnumProperty.create("position", ParticlesPosition.class);
	public static boolean showShape = false;
	private static int locateCounter = 0;
	private static final int MAX_TO_LOCATE = 16;

	public ParticleGeneratorBlock(AbstractBlock.Properties properties)
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(PARTICLES_POSITION, ParticlesPosition.CENTER));
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(PARTICLES_POSITION);
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader blockReader)
	{
		return new ParticleGeneratorTileEntity();
	}

	@Override @Deprecated
	public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, ISelectionContext ctx)
	{
		if (showShape)
		{
			return super.getShape(blockState, blockReader, blockPos, ctx);
		}
		else
		{
			return VoxelShapes.empty();
		}
	}

	@Override
	public boolean propagatesSkylightDown(BlockState blockState, IBlockReader blockReader, BlockPos blockPos)
	{
		return true;
	}

	@Override @Deprecated
	public float getShadeBrightness(BlockState blockState, IBlockReader blockReader, BlockPos blockPos)
	{
		return 1.0F;
	}

	@Override @OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random)
	{
		TileEntity tileEntity = world.getBlockEntity(blockPos);
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

	public static void locate()
	{
		locateCounter = MAX_TO_LOCATE;
	}
}
