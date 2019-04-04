package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.Chest;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import org.bukkit.block.data.type.Chest.Type;

public abstract class CraftChest extends CraftBlockData implements Chest {

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
