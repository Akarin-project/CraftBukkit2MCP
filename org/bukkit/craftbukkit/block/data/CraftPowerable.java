package org.bukkit.craftbukkit.block.data;

import org.bukkit.block.data.Powerable;

public abstract class CraftPowerable extends CraftBlockData implements Powerable {

    private static final minecraft.block.properties.PropertyBool POWERED = getBoolean("powered");

    @Override
    public boolean isPowered() {
        return get(POWERED);
    }

    @Override
    public void setPowered(boolean powered) {
        set(POWERED, powered);
    }
}
