package com.mt1006.pgen.pgen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class ParticleGeneratorBlockEntityRenderer implements BlockEntityRenderer<ParticleGeneratorBlockEntity>
{
	public ParticleGeneratorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

	@Override public void render(ParticleGeneratorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
								 MultiBufferSource bufferSource, int packedLight, int packedOverlay)
	{
		if (ParticleGeneratorBlock.showShape)
		{
			BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
			BlockState blockState = blockEntity.getBlockState();
			BakedModel model = blockRenderer.getBlockModel(blockState);
			blockRenderer.getModelRenderer().renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.cutout()),
					blockState, model, 1.0f, 1.0f, 1.0f, LightTexture.FULL_BRIGHT, packedOverlay);
		}
	}
}
