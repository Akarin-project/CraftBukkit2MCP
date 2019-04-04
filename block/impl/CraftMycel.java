/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftMycel extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Snowable {

    public CraftMycel() {
        super();
    }

    public CraftMycel(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftSnowable

    private static final minecraft.block.properties.PropertyBool SNOWY = getBoolean(net.minecraft.block.BlockMycelium.class, "snowy");

    @Override
    public boolean isSnowy() {
        return get(SNOWY);
    }

    @Override
    public void setSnowy(boolean snowy) {
        set(SNOWY, snowy);
    }
}
