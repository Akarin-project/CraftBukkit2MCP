/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftFluids extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Levelled {

    public CraftFluids() {
        super();
    }

    public CraftFluids(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftLevelled

    private static final minecraft.block.properties.PropertyInteger LEVEL = getInteger(net.minecraft.block.BlockLiquid.class, "level");

    @Override
    public int getLevel() {
        return get(LEVEL);
    }

    @Override
    public void setLevel(int level) {
        set(LEVEL, level);
    }

    @Override
    public int getMaximumLevel() {
        return getMax(LEVEL);
    }
}
