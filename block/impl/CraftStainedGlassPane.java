/**
 * Automatically generated file, changes will be lost.
 */
package org.bukkit.craftbukkit.block.impl;

public final class CraftStainedGlassPane extends org.bukkit.craftbukkit.block.data.CraftBlockData implements org.bukkit.block.data.type.GlassPane, org.bukkit.block.data.MultipleFacing, org.bukkit.block.data.Waterlogged {

    public CraftStainedGlassPane() {
        super();
    }

    public CraftStainedGlassPane(minecraft.block.state.IBlockState state) {
        super(state);
    }

    // org.bukkit.craftbukkit.block.data.CraftMultipleFacing

    private static final minecraft.block.properties.PropertyBool[] FACES = new minecraft.block.properties.PropertyBool[]{
        getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "north", true), getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "east", true), getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "south", true), getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "west", true), getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "up", true), getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "down", true)
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

    // org.bukkit.craftbukkit.block.data.CraftWaterlogged

    private static final minecraft.block.properties.PropertyBool WATERLOGGED = getBoolean(net.minecraft.block.BlockStainedGlassPane.class, "waterlogged");

    @Override
    public boolean isWaterlogged() {
        return get(WATERLOGGED);
    }

    @Override
    public void setWaterlogged(boolean waterlogged) {
        set(WATERLOGGED, waterlogged);
    }
}
