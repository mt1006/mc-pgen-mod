package com.mt1006.ParticleGenerator.util;

import com.mojang.math.Vector3d;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;

public class Utils
{
    public static ListTag newDoubleListNBT(double... input)
    {
        ListTag list = new ListTag();
        for (double val : input)
        {
            list.add(DoubleTag.valueOf(val));
        }
        return list;
    }

    public static Vector3d newVector3dFromNBT(CompoundTag nbt, String name)
    {
        ListTag motionList = nbt.getList(name, 6); // 6 - double
        double x = motionList.getDouble(0);
        double y = motionList.getDouble(1);
        double z = motionList.getDouble(2);
        return new Vector3d(x, y, z);
    }
}
