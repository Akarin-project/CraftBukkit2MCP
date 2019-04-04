/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftVine extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.MultipleFacing {

    public CraftVine() {
        super();
    }

    public CraftVine(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftMultipleFacing

    private static final minecraft.block.properties.PropertyBool[] FACES = new minecraft.block.properties.PropertyBool[]{
        getBoolean(net.minecraft.block.BlockVine.class, "north", true), getBoolean(net.minecraft.block.BlockVine.class, "east", true), getBoolean(net.minecraft.block.BlockVine.class, "south", true), getBoolean(net.minecraft.block.BlockVine.class, "west", true), getBoolean(net.minecraft.block.BlockVine.class, "up", true), getBoolean(net.minecraft.block.BlockVine.class, "down", true)
    };

    @Override
    public boolean hasFace(org.bukkit.block.BlockFace face) {
        return get(FACES[face.ordinal()]);
    }

    @Override
    public void setFace(org.bukkit.block.BlockFace face, boolean has) {
        set(FACES[face.ordinal()], has);
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getFaces() {
        com.google.common.collect.ImmutableSet.Builder<org.bukkit.block.BlockFace> faces = com.google.common.collect.ImmutableSet.builder();

        for (int i = 0; i < FACES.length; i++) {
            if (FACES[i] != null && get(FACES[i])) {
                faces.add(org.bukkit.block.BlockFace.values()[i]);
            }
        }

        return faces.build();
    }

    @Override
    public java.util.Set<org.bukkit.block.BlockFace> getAllowedFaces() {
        com.google.common.collect.ImmutableSet.Builder<org.bukkit.block.BlockFace> faces = com.google.common.collect.ImmutableSet.builder();

        for (int i = 0; i < FACES.length; i++) {
            if (FACES[i] != null) {
                faces.add(org.bukkit.block.BlockFace.values()[i]);
            }
        }

        return faces.build();
    }
}
