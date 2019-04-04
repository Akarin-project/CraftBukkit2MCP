/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;
import org.bukkit.block.data.type.TechnicalPiston.Type;


public final class CraftPistonMoving extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.TechnicalPiston, org.bukkit.block.data.Directional {

    public CraftPistonMoving() {
        super();
    }

    public CraftPistonMoving(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftTechnicalPiston

    private static final minecraft.block.properties.PropertyEnum<?> TYPE = getEnum(net.minecraft.block.BlockPistonMoving.class, "type");

    @Override
    public Type getType() {
        return get(TYPE, Type.class);
    }

    @Override
    public void setType(Type type) {
        set(TYPE, type);
    }

    // org.bukkit.craftbukkit.block.data.CraftDirectional

    private static final minecraft.block.properties.PropertyEnum<?> FACING = getEnum(net.minecraft.block.BlockPistonMoving.class, "facing");

    @Override
    public org.bukkit.block.BlockFace getFacing() {
        return get(FACING, org.bukkit.block.BlockFace.class);
    }

    @Override
    public void setFacing(org.bukkit.block.BlockFace facing) {
        set(FACING, facing);
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getFaces() {
        return getValues(FACING, org.bukkit.block.BlockFace.class);
    }
}
