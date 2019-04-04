package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.Door;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import org.bukkit.block.data.type.Door.Hinge;

public abstract class CraftDoor extends CraftBlockData implements Door {

    private static final minecraft.block.properties.PropertyEnum<?> HINGE = getEnum("hinge");

    @Override
    public Hinge getHinge() {
        return get(HINGE, Hinge.class);
    }

    @Override
    public void setHinge(Hinge hinge) {
        set(HINGE, hinge);
    }
}
