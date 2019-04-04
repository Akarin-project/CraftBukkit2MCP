/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftSoil extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.Farmland {

    public CraftSoil() {
        super();
    }

    public CraftSoil(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftFarmland

    private static final minecraft.block.properties.PropertyInteger MOISTURE = getInteger(net.minecraft.block.BlockFarmland.class, "moisture");

    @Override
    public int getMoisture() {
        return get(MOISTURE);
    }

    @Override
    public void setMoisture(int moisture) {
        set(MOISTURE, moisture);
    }

    @Override
    public int getMaximumMoisture() {
        return getMax(MOISTURE);
    }
}
