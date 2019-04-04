/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;
import org.bukkit.block.data.Bisected.Half;


public final class CraftTallSeaGrass extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Bisected {

    public CraftTallSeaGrass() {
        super();
    }

    public CraftTallSeaGrass(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftBisected

    private static final minecraft.block.properties.PropertyEnum<?> HALF = getEnum(net.minecraft.server.BlockTallSeaGrass.class, "half");

    @Override
    public Half getHalf() {
        return get(HALF, Half.class);
    }

    @Override
    public void setHalf(Half half) {
        set(HALF, half);
    }
}
