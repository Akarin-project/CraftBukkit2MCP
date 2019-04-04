/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;
import org.bukkit.block.data.type.RedstoneWire.Connection;


public final class CraftRedstoneWire extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.RedstoneWire, org.bukkit.block.data.AnaloguePowerable {

    public CraftRedstoneWire() {
        super();
    }

    public CraftRedstoneWire(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.type.CraftRedstoneWire

    private static final minecraft.block.properties.PropertyEnum<?> NORTH = getEnum(net.minecraft.block.BlockRedstoneWire.class, "north");
    private static final minecraft.block.properties.PropertyEnum<?> EAST = getEnum(net.minecraft.block.BlockRedstoneWire.class, "east");
    private static final minecraft.block.properties.PropertyEnum<?> SOUTH = getEnum(net.minecraft.block.BlockRedstoneWire.class, "south");
    private static final minecraft.block.properties.PropertyEnum<?> WEST = getEnum(net.minecraft.block.BlockRedstoneWire.class, "west");

    @Override
    public Connection getFace(org.bukkit.block.BlockFace face) {
        switch (face) {
            case NORTH:
                return get(NORTH, Connection.class);
            case EAST:
                return get(EAST, Connection.class);
            case SOUTH:
                return get(SOUTH, Connection.class);
            case WEST:
                return get(WEST, Connection.class);
            default:
                throw new IllegalArgumentException("Cannot have face " + face);
        }
    }

    @Override
    public void setFace(org.bukkit.block.BlockFace face, Connection connection) {
        switch (face) {
            case NORTH:
                set(NORTH, connection);
            case EAST:
                set(EAST, connection);
            case SOUTH:
                set(SOUTH, connection);
            case WEST:
                set(WEST, connection);
            default:
                throw new IllegalArgumentException("Cannot have face " + face);
        }
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getAllowedFaces() {
        return com.google.common.collect.ImmutableSet.of(org.bukkit.block.BlockFace.NORTH, org.bukkit.block.BlockFace.EAST, org.bukkit.block.BlockFace.SOUTH, org.bukkit.block.BlockFace.WEST);
    }

    // org.bukkit.craftbukkit.block.data.CraftAnaloguePowerable

    private static final minecraft.block.properties.PropertyInteger POWER = getInteger(net.minecraft.block.BlockRedstoneWire.class, "power");

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
