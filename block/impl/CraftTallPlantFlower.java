/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;
import org.bukkit.block.data.Bisected.Half;


public final class CraftTallPlantFlower extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Bisected {

    public CraftTallPlantFlower() {
        super();
    }

    public CraftTallPlantFlower(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftBisected

    private static final minecraft.block.properties.PropertyEnum<?> HALF = getEnum(net.minecraft.server.BlockTallPlantFlower.class, "half");

    @Override
    public Half getHalf() {
        return get(HALF, Half.class);
    }

    @Override
    public void setHalf(Half half) {
        set(HALF, half);
    }
}
