package com.mt1006.pgen.pgen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class ParticleGeneratorBlockEntityRenderer implements BlockEntityRenderer<ParticleGeneratorBlockEntity, ParticleGeneratorBlockEntityRenderer.RenderState>
{
	private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
	private final BlockModelResolver modelResolver;

	public ParticleGeneratorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx)
	{
		this.modelResolver = ctx.blockModelResolver();
	}

	@Override public RenderState createRenderState()
	{
		return new RenderState();
	}

	@Override public void extractRenderState(ParticleGeneratorBlockEntity blockEntity, RenderState state, float partialTicks,
											 Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress)
	{
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
		modelResolver.update(state.block, blockEntity.getBlockState(), BLOCK_DISPLAY_CONTEXT);
	}

	@Override public void submit(RenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState)
	{
		if (ParticleGeneratorBlock.showShape)
		{
			state.block.submit(poseStack, nodeCollector, 15728880 /* full bright */, OverlayTexture.NO_OVERLAY, 0);
		}
	}

	public static class RenderState extends BlockEntityRenderState
	{
		public final BlockModelRenderState block = new BlockModelRenderState();
	}
}
