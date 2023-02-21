package com.mt1006.ParticleGenerator.utils;

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

    public static Vec3 vector3dFromNBT(CompoundTag nbt, String name)
    {
        ListTag motionList = nbt.getList(name, 6); // 6 - double
        double x = motionList.getDouble(0);
        double y = motionList.getDouble(1);
        double z = motionList.getDouble(2);
        return new Vec3(x, y, z);
    }

    public static ResourceLocation resourceLocationFromString(String id)
    {
        ResourceLocation resLoc;
        try { resLoc = new ResourceLocation(id.toLowerCase()); }
        catch (Exception exception) { resLoc = null; }
        return resLoc;
    }
}
