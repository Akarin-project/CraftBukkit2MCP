/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftPressurePlateWeighted extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.AnaloguePowerable {

    public CraftPressurePlateWeighted() {
        super();
    }

    public CraftPressurePlateWeighted(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final minecraft.block.properties.PropertyInteger POWER = getInteger(net.minecraft.block.BlockPressurePlateWeighted.class, "power");

    @Override
    public int getPower() {
        return get(POWER);
    }

    @Override
    public void setPower(int power) {
        set(POWER, power);
    }

    @Override
    public int getMaximumPower() {
        return getMax(POWER);
    }
}
