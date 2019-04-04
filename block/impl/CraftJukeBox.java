/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftJukeBox extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.Jukebox {

    public CraftJukeBox() {
        super();
    }

    public CraftJukeBox(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftJukebox

    private static final minecraft.block.properties.PropertyBool HAS_RECORD = getBoolean(net.minecraft.block.BlockJukebox.class, "has_record");

    @Override
    public boolean hasRecord() {
        return get(HAS_RECORD);
    }
}
