package com.mt1006.ParticleGenerator.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3d;

public class Utils
{
    public static ListNBT newDoubleListNBT(double... input)
    {
        ListNBT list = new ListNBT();
        for (double val : input)
        {
            list.add(DoubleNBT.valueOf(val));
        }
        return list;
    }

    public static Vector3d newVector3dFromNBT(CompoundNBT nbt, String name)
    {
        ListNBT motionList = nbt.getList(name, 6); // 6 - double
        double x = motionList.getDouble(0);
        double y = motionList.getDouble(1);
        double z = motionList.getDouble(2);
        return new Vector3d(x, y, z);
    }
}
