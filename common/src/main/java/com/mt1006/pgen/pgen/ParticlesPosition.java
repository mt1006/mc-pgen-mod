package com.mt1006.pgen.pgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public enum ParticlesPosition implements StringRepresentable
{
	TOP(0.5, 1.0, 0.5),
	CENTER(0.5, 0.5, 0.5),
	BOTTOM(0.5, 0.0, 0.5);

	private final String name;
	private final double x, y, z;

	ParticlesPosition(double x, double y, double z)
	{
		this.name = name().toLowerCase();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override public String toString()
	{
		return name;
	}

	@Override public @NotNull String getSerializedName()
	{
		return name;
	}

	public Vec3 getFinalPosition(BlockPos blockPos)
	{
		return new Vec3((double)blockPos.getX() + x, (double)blockPos.getY() + y, (double)blockPos.getZ() + z);
	}
}
