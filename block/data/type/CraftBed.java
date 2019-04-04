package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.Bed;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import org.bukkit.block.data.type.Bed.Part;

public abstract class CraftBed extends CraftBlockData implements Bed {

    private static final minecraft.block.properties.PropertyEnum<?> PART = getEnum("part");
    private static final minecraft.block.properties.PropertyBool OCCUPIED = getBoolean("occupied");

    @Override
    public Part getPart() {
        return get(PART, Part.class);
    }

    @Override
    public void setPart(Part part) {
        set(PART, part);
    }

    @Override
    public boolean isOccupied() {
        return get(OCCUPIED);
    }
}
