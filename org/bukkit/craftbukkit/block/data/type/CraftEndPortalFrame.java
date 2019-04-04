package org.bukkit.craftbukkit.block.data.type;

import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

public abstract class CraftEndPortalFrame extends CraftBlockData implements EndPortalFrame {

    private static final minecraft.block.properties.PropertyBool EYE = getBoolean("eye");

    @Override
    public boolean hasEye() {
        return get(EYE);
    }

    @Override
    public void setEye(boolean eye) {
        set(EYE, eye);
    }
}
