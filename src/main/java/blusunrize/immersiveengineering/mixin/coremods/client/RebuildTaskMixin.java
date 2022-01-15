/*
 * BluSunrize
 * Copyright (c) 2022
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.mixin.coremods.client;

import blusunrize.immersiveengineering.client.render.ConnectionRenderer;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Set;

@Mixin(targets = "net.minecraft.client.renderer.chunk.ChunkRenderDispatcher$RenderChunk$RebuildTask")
public class RebuildTaskMixin
{
	@Shadow
	@Nullable
	protected RenderChunkRegion region;
	private RenderChunkRegion regionCopy;
	@Shadow(aliases = "f_112859_")
	@Final
	private RenderChunk this$1;

	@Inject(
			method = "compile",
			at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Sets;newHashSet()Ljava/util/HashSet;", remap = false)
	)
	public void extractRegion(CallbackInfoReturnable<Set<BlockEntity>> cir)
	{
		//TODO figure out why this is necessary: Is vanilla doing a dumb non-atomic swap and hoping it will behave
		// atomically?
		regionCopy = region;
	}

	@Inject(
			method = "compile",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;enableCaching()V")
	)
	public void addConnectionQuads(
			float pX, float pY, float pZ,
			ChunkRenderDispatcher.CompiledChunk pCompiledChunk,
			ChunkBufferBuilderPack pBuffers,
			CallbackInfoReturnable<Set<BlockEntity>> cir
	)
	{
		ConnectionRenderer.renderConnectionsInSection(pCompiledChunk, pBuffers, this.regionCopy, this$1);
		this.regionCopy = null;
	}
}
