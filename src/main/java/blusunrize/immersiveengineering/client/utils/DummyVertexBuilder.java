/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 *
 */

package blusunrize.immersiveengineering.client.utils;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import javax.annotation.Nonnull;

public record DummyVertexBuilder(VertexFormat format) implements VertexConsumer
{
	@Nonnull
	@Override
	public VertexConsumer vertex(double x, double y, double z)
	{
		return this;
	}

	@Nonnull
	@Override
	public VertexConsumer color(int red, int green, int blue, int alpha)
	{
		return this;
	}

	@Nonnull
	@Override
	public VertexConsumer uv(float u, float v)
	{
		return this;
	}

	@Nonnull
	@Override
	public VertexConsumer overlayCoords(int u, int v)
	{
		return this;
	}

	@Nonnull
	@Override
	public VertexConsumer uv2(int u, int v)
	{
		return this;
	}

	@Nonnull
	@Override
	public VertexConsumer normal(float x, float y, float z)
	{
		return this;
	}

	@Override
	public void endVertex()
	{
	}

	@Override
	public void defaultColor(int r, int g, int b, int a)
	{
	}

	@Override
	public void unsetDefaultColor()
	{

	}

	@Override
	@Nonnull
	public VertexFormat getVertexFormat()
	{
		return format;
	}
}
