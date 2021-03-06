package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import org.bukkit.block.data.type.RedstoneWire.Connection;

public abstract class CraftRedstoneWire extends CraftBlockData implements RedstoneWire {

    private static final minecraft.block.properties.PropertyEnum<?> NORTH = getEnum("north");
    private static final minecraft.block.properties.PropertyEnum<?> EAST = getEnum("east");
    private static final minecraft.block.properties.PropertyEnum<?> SOUTH = getEnum("south");
    private static final minecraft.block.properties.PropertyEnum<?> WEST = getEnum("west");

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
}
