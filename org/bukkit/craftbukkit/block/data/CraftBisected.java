package org.bukkit.craftbukkit.block.data;

import org.bukkit.block.data.Bisected;

import org.bukkit.block.data.Bisected.Half;

public class CraftBisected extends CraftBlockData implements Bisected {

    private static final minecraft.block.properties.PropertyEnum<?> HALF = getEnum("half");

    @Override
    public Half getHalf() {
        return get(HALF, Half.class);
    }

    @Override
    public void setHalf(Half half) {
        set(HALF, half);
    }
}
