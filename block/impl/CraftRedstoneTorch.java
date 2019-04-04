/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftRedstoneTorch extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.Lightable {

    public CraftRedstoneTorch() {
        super();
    }

    public CraftRedstoneTorch(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftLightable

    private static final minecraft.block.properties.PropertyBool LIT = getBoolean(net.minecraft.block.BlockRedstoneTorch.class, "lit");

    @Override
    public boolean isLit() {
        return get(LIT);
    }

    @Override
    public void setLit(boolean lit) {
        set(LIT, lit);
    }
}
