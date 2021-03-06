package org.bukkit.craftbukkit.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

public abstract class CraftProjectile extends AbstractProjectile implements Projectile {
    public CraftProjectile(CraftServer server, net.minecraft.entity.Entity entity) {
        super(server, entity);
    }

    public ProjectileSource getShooter() {
        return getHandle().projectileSource;
    }

    public void setShooter(ProjectileSource shooter) {
        if (shooter instanceof CraftLivingEntity) {
            getHandle().thrower = (EntityLivingBase) ((CraftLivingEntity) shooter).entity;
            getHandle().shooterId = ((CraftLivingEntity) shooter).getUniqueId();
        } else {
            getHandle().thrower = null;
            getHandle().shooterId = null;
        }
        getHandle().projectileSource = shooter;
    }

    @Override
    public EntityThrowable getHandle() {
        return (EntityThrowable) entity;
    }

    @Override
    public String toString() {
        return "CraftProjectile";
    }
}
