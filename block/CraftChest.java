package org.bukkit.craftbukkit.block;

import net.minecraft.block.BlockChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.world.ILockableContainer;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntityChest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest;
import org.bukkit.inventory.Inventory;

public class CraftChest extends CraftLootable<TileEntityChest> implements Chest {

    public CraftChest(final Block block) {
        super(block, TileEntityChest.class);
    }

    public CraftChest(final Material material, final TileEntityChest te) {
        super(material, te);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventory(this.getSnapshot());
    }

    @Override
    public Inventory getBlockInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventory(this.getTileEntity());
    }

    @Override
    public Inventory getInventory() {
        CraftInventory inventory = (CraftInventory) this.getBlockInventory();
        if (!isPlaced()) {
            return inventory;
        }

        // The logic here is basically identical to the logic in BlockChest.interact
        int x = this.getX();
        int y = this.getY();
        int z = this.getZ();
        CraftWorld world = (CraftWorld) this.getWorld();

        ILockableContainer nms = ((BlockChest) Blocks.CHEST).getInventory(data, world.getHandle(), new BlockPos(x, y, z), true);

        if (nms instanceof InventoryLargeChest) {
            inventory = new CraftInventoryDoubleChest((InventoryLargeChest) nms);
        }
        return inventory;
    }
}
