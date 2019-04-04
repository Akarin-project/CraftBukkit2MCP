package org.bukkit.craftbukkit.block.data;

import org.bukkit.block.data.Waterlogged;

public abstract class CraftWaterlogged extends CraftBlockData implements Waterlogged {

    private static final minecraft.block.properties.PropertyBool WATERLOGGED = getBoolean("waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
