package com.mt1006.ParticleGenerator.pgen.blockstate;

import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ParticlesPosition implements StringRepresentable
{
	TOP("top", 0.5, 1.0, 0.5),
	CENTER("center", 0.5, 0.5, 0.5),
	BOTTOM("bottom", 0.5, 0.0, 0.5);

	private final String name;
	private final double x, y, z;

	ParticlesPosition(String name, double x, double y, double z)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public @NotNull String getSerializedName()
	{
		return name;
	}

	public Vector3d getFinalPosition(BlockPos blockPos)
	{
		return new Vector3d((double)blockPos.getX() + x, (double)blockPos.getY() + y, (double)blockPos.getZ() + z);
	}
}
