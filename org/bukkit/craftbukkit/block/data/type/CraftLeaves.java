package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

public class CraftLeaves extends CraftBlockData implements Leaves {

    private static final minecraft.block.properties.PropertyInteger DISTANCE = getInteger("distance");
    private static final minecraft.block.properties.PropertyBool PERSISTENT = getBoolean("persistent");

    @Override
    public boolean isPersistent() {
        return get(PERSISTENT);
    }

    @Override
    public void setPersistent(boolean persistent) {
        set(PERSISTENT, persistent);
    }

    @Override
    public int getDistance() {
        return get(DISTANCE);
    }

    @Override
    public void setDistance(int distance) {
        set(DISTANCE, distance);
    }
}
