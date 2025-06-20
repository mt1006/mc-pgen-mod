package com.mt1006.pgen.utils;

import net.minecraft.resources.ResourceLocation;

public class Utils
{
	public static ResourceLocation resourceLocationFromString(String id)
	{
		ResourceLocation resLoc;
		try { resLoc = ResourceLocation.parse(id.toLowerCase()); }
		catch (Exception exception) { resLoc = null; }
		return resLoc;
	}
}
