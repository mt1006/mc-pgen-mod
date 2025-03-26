package com.mt1006.pgen.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class Utils
{
	public static ListTag doubleListToNBT(double... input)
	{
		ListTag list = new ListTag();
		for (double val : input)
		{
			list.add(DoubleTag.valueOf(val));
		}
		return list;
	}

	public static Vec3 vector3dFromNBT(CompoundTag nbt, String name, Vec3 def)
	{
		ListTag motionList = nbt.getList(name).orElse(null);
		if (motionList == null) { return def; }

		double x = motionList.getDoubleOr(0, def.x);
		double y = motionList.getDoubleOr(1, def.y);
		double z = motionList.getDoubleOr(2, def.z);
		return new Vec3(x, y, z);
	}

	public static ResourceLocation resourceLocationFromString(String id)
	{
		ResourceLocation resLoc;
		try { resLoc = ResourceLocation.parse(id.toLowerCase()); }
		catch (Exception exception) { resLoc = null; }
		return resLoc;
	}
}
