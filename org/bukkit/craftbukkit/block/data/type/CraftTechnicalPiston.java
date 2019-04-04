package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.TechnicalPiston;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import org.bukkit.block.data.type.TechnicalPiston.Type;

public abstract class CraftTechnicalPiston extends CraftBlockData implements TechnicalPiston {

    private static final minecraft.block.properties.PropertyEnum<?> TYPE = getEnum("type");

    @Override
    public Type getType() {
        return get(TYPE, Type.class);
    }

    @Override
    public void setType(Type type) {
        set(TYPE, type);
    }
}
