package com.mt1006.ParticleGenerator.state;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public enum ParticlesPosition implements IStringSerializable
{
	TOP("top", 0.5, 1.0, 0.5),
	CENTER("center", 0.5, 0.5, 0.5),
	BOTTOM("bottom", 0.5, 0.0, 0.5);

	private final String name;
	private double x, y, z;

	private ParticlesPosition(String name, double x, double y, double z)
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
	public String getSerializedName()
	{
		return name;
	}

	public Vector3d getFinalPosition(BlockPos blockPos)
	{
		return new Vector3d((double)blockPos.getX() + x, (double)blockPos.getY() + y, (double)blockPos.getZ() + z);
	}
}
