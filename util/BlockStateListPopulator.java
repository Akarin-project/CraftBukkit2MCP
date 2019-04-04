package org.bukkit.craftbukkit.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.block.CraftBlockState;

public class BlockStateListPopulator {
    private final World world;
    private final List<CraftBlockState> list;

    public BlockStateListPopulator(World world) {
        this(world, new ArrayList<CraftBlockState>());
    }

    public BlockStateListPopulator(World world, List<CraftBlockState> list) {
        this.world = world;
        this.list = list;
    }

    public void setTypeUpdate(BlockPos position, IBlockState data) {
        CraftBlockState state = CraftBlockState.getBlockState(world, position);
        state.setData(data);
        list.add(state);
    }

    public void setTypeAndData(BlockPos position, IBlockState data, int flag) {
        CraftBlockState state = CraftBlockState.getBlockState(world, position, flag);
        state.setData(data);
        list.add(state);
    }

    public void updateList() {
        for (BlockState state : list) {
            state.update(true);
        }
    }

    public List<CraftBlockState> getList() {
        return list;
    }

    public World getWorld() {
        return world;
    }
}
