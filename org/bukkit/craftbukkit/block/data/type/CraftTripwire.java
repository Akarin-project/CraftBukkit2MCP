package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.Tripwire;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

public abstract class CraftTripwire extends CraftBlockData implements Tripwire {

    private static final minecraft.block.properties.PropertyBool DISARMED = getBoolean("disarmed");

    @Override
    public boolean isDisarmed() {
        return get(DISARMED);
    }

    @Override
    public void setDisarmed(boolean disarmed) {
        set(DISARMED, disarmed);
    }
}
