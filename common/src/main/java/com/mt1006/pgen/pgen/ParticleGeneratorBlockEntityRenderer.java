package com.mt1006.pgen.pgen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ParticleGeneratorBlockEntityRenderer implements BlockEntityRenderer<ParticleGeneratorBlockEntity, BlockEntityRenderState>
{
	public ParticleGeneratorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

	@Override public BlockEntityRenderState createRenderState()
	{
		return new BlockEntityRenderState();
	}

	@Override public void submit(BlockEntityRenderState renderState, PoseStack poseStack,
								 SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState)
	{
		if (ParticleGeneratorBlock.showShape)
		{
			BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
			BlockStateModel model = blockRenderer.getBlockModel(renderState.blockState);
			nodeCollector.submitBlockModel(poseStack, RenderType.cutout(), model,
					1.0f, 1.0f, 1.0f, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0);
		}
	}
}
