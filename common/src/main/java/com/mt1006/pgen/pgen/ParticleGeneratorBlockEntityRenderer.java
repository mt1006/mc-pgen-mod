package com.mt1006.pgen.pgen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ParticleGeneratorBlockEntityRenderer implements BlockEntityRenderer<ParticleGeneratorBlockEntity>
{
	public ParticleGeneratorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

	@Override public void render(ParticleGeneratorBlockEntity blockEntity, float partialTick, PoseStack poseStack,
								 MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 vec3)
	{
		if (ParticleGeneratorBlock.showShape)
		{
			BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
			BlockState blockState = blockEntity.getBlockState();
			BlockStateModel model = blockRenderer.getBlockModel(blockState);
			ModelBlockRenderer.renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.cutout()),
					model, 1.0f, 1.0f, 1.0f, LightTexture.FULL_BRIGHT, packedOverlay);
		}
	}
}
