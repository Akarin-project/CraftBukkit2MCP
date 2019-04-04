package org.bukkit.craftbukkit.block;

import net.minecraft.block.BlockJukebox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.server.TileEntityJukeBox;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public class CraftJukebox extends CraftBlockEntityState<TileEntityJukeBox> implements Jukebox {

    public CraftJukebox(final Block block) {
        super(block, TileEntityJukeBox.class);
    }

    public CraftJukebox(final Material material, TileEntityJukeBox te) {
        super(material, te);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && this.isPlaced() && this.getType() == Material.JUKEBOX) {
            CraftWorld world = (CraftWorld) this.getWorld();
            Material record = this.getPlaying();
            if (record == Material.AIR) {
                world.getHandle().setTypeAndData(new BlockPos(this.getX(), this.getY(), this.getZ()),
                    Blocks.JUKEBOX.getDefaultState()
                        .set(BlockJukebox.HAS_RECORD, false), 3);
            } else {
                world.getHandle().setTypeAndData(new BlockPos(this.getX(), this.getY(), this.getZ()),
                    Blocks.JUKEBOX.getDefaultState()
                        .set(BlockJukebox.HAS_RECORD, true), 3);
            }
            world.playEffect(this.getLocation(), Effect.RECORD_PLAY, Item.getIdFromItem(CraftMagicNumbers.getItem((Material) record)));
        }

        return result;
    }

    @Override
    public Material getPlaying() {
        ItemStack record = this.getSnapshot().getRecord();
        if (record.isEmpty()) {
            return Material.AIR;
        }
        return CraftMagicNumbers.getMaterial(record.getItem());
    }

    @Override
    public void setPlaying(Material record) {
        if (record == null || CraftMagicNumbers.getItem(record) == null) {
            record = Material.AIR;
        }

        this.getSnapshot().setRecord(new ItemStack(CraftMagicNumbers.getItem(record), 1));
        if (record == Material.AIR) {
            setRawData((byte) 0);
        } else {
            setRawData((byte) 1);
        }
    }

    @Override
    public boolean isPlaying() {
        return getRawData() == 1;
    }

    @Override
    public boolean eject() {
        requirePlaced();
        TileEntity tileEntity = this.getTileEntityFromWorld();
        if (!(tileEntity instanceof TileEntityJukeBox)) return false;

        TileEntityJukeBox jukebox = (TileEntityJukeBox) tileEntity;
        boolean result = !jukebox.getRecord().isEmpty();
        CraftWorld world = (CraftWorld) this.getWorld();
        ((BlockJukebox) Blocks.JUKEBOX).dropRecord(world.getHandle(), new BlockPos(getX(), getY(), getZ()));
        return result;
    }
}
