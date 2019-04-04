package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import org.bukkit.block.data.type.Stairs.Shape;

public abstract class CraftStairs extends CraftBlockData implements Stairs {

    private static final minecraft.block.properties.PropertyEnum<?> SHAPE = getEnum("shape");

    @Override
    public Shape getShape() {
        return get(SHAPE, Shape.class);
    }

    @Override
    public void setShape(Shape shape) {
        set(SHAPE, shape);
    }
}
