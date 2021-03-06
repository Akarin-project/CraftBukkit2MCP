package org.bukkit.craftbukkit.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;

public class CraftBlock implements Block {
    private final net.minecraft.server.GeneratorAccess world;
    private final BlockPos position;

    public CraftBlock(GeneratorAccess world, BlockPos position) {
        this.world = world;
        this.position = position;
    }

    public static CraftBlock at(GeneratorAccess world, BlockPos position) {
        return new CraftBlock(world, position);
    }

    private net.minecraft.block.Block getNMSBlock() {
        return getNMS().getBlock();
    }

    protected minecraft.block.state.IBlockState getNMS() {
        return world.getBlockState(position);
    }

    public World getWorld() {
        return world.getMinecraftWorld().getWorld();
    }

    public CraftWorld getCraftWorld() {
        return (CraftWorld) getWorld();
    }

    public Location getLocation() {
        return new Location(getWorld(), position.getX(), position.getY(), position.getZ());
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(position.getX());
            loc.setY(position.getY());
            loc.setZ(position.getZ());
            loc.setYaw(0);
            loc.setPitch(0);
        }

        return loc;
    }

    public BlockVector getVector() {
        return new BlockVector(getX(), getY(), getZ());
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getZ() {
        return position.getZ();
    }

    public Chunk getChunk() {
        return getWorld().getChunkAt(this);
    }

    public void setData(final byte data) {
        setData(data, 3);
    }

    public void setData(final byte data, boolean applyPhysics) {
        if (applyPhysics) {
            setData(data, 3);
        } else {
            setData(data, 2);
        }
    }

    private void setData(final byte data, int flag) {
        world.setTypeAndData(position, CraftMagicNumbers.getBlock(getType(), data), flag);
    }

    private IBlockState getData0() {
        return world.getBlockState(position);
    }

    public byte getData() {
        IBlockState blockData = world.getBlockState(position);
        return CraftMagicNumbers.toLegacyData(blockData);
    }

    @Override
    public BlockData getBlockData() {
       return CraftBlockData.fromData(getData0());
    }

    public void setType(final Material type) {
        setType(type, true);
    }

    @Override
    public void setType(Material type, boolean applyPhysics) {
        setBlockData(type.createBlockData(), applyPhysics);
    }

    @Override
    public void setBlockData(BlockData data) {
        setBlockData(data, true);
    }

    @Override
    public void setBlockData(BlockData data, boolean applyPhysics) {
        setTypeAndData(((CraftBlockData) data).getState(), applyPhysics);
    }

    public boolean setTypeAndData(final IBlockState blockData, final boolean applyPhysics) {
        // SPIGOT-611: need to do this to prevent glitchiness. Easier to handle this here (like /setblock) than to fix weirdness in tile entity cleanup
        if (!blockData.isAir() && blockData.getBlock() instanceof BlockContainer && blockData.getBlock() != getNMSBlock()) {
            world.setTypeAndData(position, Blocks.AIR.getDefaultState(), 0);
        }

        if (applyPhysics) {
            return world.setTypeAndData(position, blockData, 3);
        } else {
            IBlockState old = world.getBlockState(position);
            boolean success = world.setTypeAndData(position, blockData, 18); // NOTIFY | NO_OBSERVER
            if (success) {
                world.getMinecraftWorld().notifyBlockUpdate(
                        position,
                        old,
                        blockData,
                        3
                );
            }
            return success;
        }
    }

    public Material getType() {
        return CraftMagicNumbers.getMaterial(world.getBlockState(position).getBlock());
    }

    public byte getLightLevel() {
        return (byte) world.getMinecraftWorld().getLightLevel(position);
    }

    public byte getLightFromSky() {
        return (byte) world.getBrightness(EnumSkyBlock.SKY, position);
    }

    public byte getLightFromBlocks() {
        return (byte) world.getBrightness(EnumSkyBlock.BLOCK, position);
    }


    public Block getFace(final BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getFace(final BlockFace face, final int distance) {
        return getRelative(face, distance);
    }

    public Block getRelative(final int modX, final int modY, final int modZ) {
        return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
    }

    public Block getRelative(BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getRelative(BlockFace face, int distance) {
        return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }

    public BlockFace getFace(final Block block) {
        BlockFace[] values = BlockFace.values();

        for (BlockFace face : values) {
            if ((this.getX() + face.getModX() == block.getX()) &&
                (this.getY() + face.getModY() == block.getY()) &&
                (this.getZ() + face.getModZ() == block.getZ())
            ) {
                return face;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "CraftBlock{pos=" + position + ",type=" + getType() + ",data=" + getNMS() + '}';
    }

    public static BlockFace notchToBlockFace(EnumFacing notch) {
        if (notch == null) return BlockFace.SELF;
        switch (notch) {
        case DOWN:
            return BlockFace.DOWN;
        case UP:
            return BlockFace.UP;
        case NORTH:
            return BlockFace.NORTH;
        case SOUTH:
            return BlockFace.SOUTH;
        case WEST:
            return BlockFace.WEST;
        case EAST:
            return BlockFace.EAST;
        default:
            return BlockFace.SELF;
        }
    }

    public static EnumFacing blockFaceToNotch(BlockFace face) {
        switch (face) {
        case DOWN:
            return EnumFacing.DOWN;
        case UP:
            return EnumFacing.UP;
        case NORTH:
            return EnumFacing.NORTH;
        case SOUTH:
            return EnumFacing.SOUTH;
        case WEST:
            return EnumFacing.WEST;
        case EAST:
            return EnumFacing.EAST;
        default:
            return null;
        }
    }

    public BlockState getState() {
        Material material = getType();

        switch (material) {
        case SIGN:
        case WALL_SIGN:
            return new CraftSign(this);
        case CHEST:
        case TRAPPED_CHEST:
            return new CraftChest(this);
        case FURNACE:
            return new CraftFurnace(this);
        case DISPENSER:
            return new CraftDispenser(this);
        case DROPPER:
            return new CraftDropper(this);
        case END_GATEWAY:
            return new CraftEndGateway(this);
        case HOPPER:
            return new CraftHopper(this);
        case SPAWNER:
            return new CraftCreatureSpawner(this);
        case JUKEBOX:
            return new CraftJukebox(this);
        case BREWING_STAND:
            return new CraftBrewingStand(this);
        case CREEPER_HEAD:
        case CREEPER_WALL_HEAD:
        case DRAGON_HEAD:
        case DRAGON_WALL_HEAD:
        case PISTON_HEAD:
        case PLAYER_HEAD:
        case PLAYER_WALL_HEAD:
        case SKELETON_SKULL:
        case SKELETON_WALL_SKULL:
        case WITHER_SKELETON_SKULL:
        case WITHER_SKELETON_WALL_SKULL:
        case ZOMBIE_HEAD:
        case ZOMBIE_WALL_HEAD:
            return new CraftSkull(this);
        case COMMAND_BLOCK:
        case CHAIN_COMMAND_BLOCK:
        case REPEATING_COMMAND_BLOCK:
            return new CraftCommandBlock(this);
        case BEACON:
            return new CraftBeacon(this);
        case BLACK_BANNER:
        case BLACK_WALL_BANNER:
        case BLUE_BANNER:
        case BLUE_WALL_BANNER:
        case BROWN_BANNER:
        case BROWN_WALL_BANNER:
        case CYAN_BANNER:
        case CYAN_WALL_BANNER:
        case GRAY_BANNER:
        case GRAY_WALL_BANNER:
        case GREEN_BANNER:
        case GREEN_WALL_BANNER:
        case LIGHT_BLUE_BANNER:
        case LIGHT_BLUE_WALL_BANNER:
        case LIGHT_GRAY_BANNER:
        case LIGHT_GRAY_WALL_BANNER:
        case LIME_BANNER:
        case LIME_WALL_BANNER:
        case MAGENTA_BANNER:
        case MAGENTA_WALL_BANNER:
        case ORANGE_BANNER:
        case ORANGE_WALL_BANNER:
        case PINK_BANNER:
        case PINK_WALL_BANNER:
        case PURPLE_BANNER:
        case PURPLE_WALL_BANNER:
        case RED_BANNER:
        case RED_WALL_BANNER:
        case WHITE_BANNER:
        case WHITE_WALL_BANNER:
        case YELLOW_BANNER:
        case YELLOW_WALL_BANNER:
            return new CraftBanner(this);
        case STRUCTURE_BLOCK:
            return new CraftStructureBlock(this);
        case SHULKER_BOX:
        case WHITE_SHULKER_BOX:
        case ORANGE_SHULKER_BOX:
        case MAGENTA_SHULKER_BOX:
        case LIGHT_BLUE_SHULKER_BOX:
        case YELLOW_SHULKER_BOX:
        case LIME_SHULKER_BOX:
        case PINK_SHULKER_BOX:
        case GRAY_SHULKER_BOX:
        case LIGHT_GRAY_SHULKER_BOX:
        case CYAN_SHULKER_BOX:
        case PURPLE_SHULKER_BOX:
        case BLUE_SHULKER_BOX:
        case BROWN_SHULKER_BOX:
        case GREEN_SHULKER_BOX:
        case RED_SHULKER_BOX:
        case BLACK_SHULKER_BOX:
            return new CraftShulkerBox(this);
        case ENCHANTING_TABLE:
            return new CraftEnchantingTable(this);
        case ENDER_CHEST:
            return new CraftEnderChest(this);
        case DAYLIGHT_DETECTOR:
            return new CraftDaylightDetector(this);
        case COMPARATOR:
            return new CraftComparator(this);
        case BLACK_BED:
        case BLUE_BED:
        case BROWN_BED:
        case CYAN_BED:
        case GRAY_BED:
        case GREEN_BED:
        case LIGHT_BLUE_BED:
        case LIGHT_GRAY_BED:
        case LIME_BED:
        case MAGENTA_BED:
        case ORANGE_BED:
        case PINK_BED:
        case PURPLE_BED:
        case RED_BED:
        case WHITE_BED:
        case YELLOW_BED:
            return new CraftBed(this);
        case CONDUIT:
            return new CraftConduit(this);
        default:
            TileEntity tileEntity = world.getTileEntity(position);
            if (tileEntity != null) {
                // block with unhandled TileEntity:
                return new CraftBlockEntityState<TileEntity>(this, (Class<TileEntity>) tileEntity.getClass());
            } else {
                // Block without TileEntity:
                return new CraftBlockState(this);
            }
        }
    }

    public Biome getBiome() {
        return getWorld().getBiome(getX(), getZ());
    }

    public void setBiome(Biome bio) {
        getWorld().setBiome(getX(), getZ(), bio);
    }

    public static Biome biomeBaseToBiome(Biome base) {
        if (base == null) {
            return null;
        }

        return Biome.valueOf(Biome.REGISTRY_ID.b(base).getKey().toUpperCase(java.util.Locale.ENGLISH));
    }

    public static Biome biomeToBiomeBase(Biome bio) {
        if (bio == null) {
            return null;
        }

        return Biome.REGISTRY_ID.get(new ResourceLocation(bio.name().toLowerCase(java.util.Locale.ENGLISH)));
    }

    public double getTemperature() {
        return getWorld().getTemperature(getX(), getZ());
    }

    public double getHumidity() {
        return getWorld().getHumidity(getX(), getZ());
    }

    public boolean isBlockPowered() {
        return world.getMinecraftWorld().getStrongPower(position) > 0;
    }

    public boolean isBlockIndirectlyPowered() {
        return world.getMinecraftWorld().isBlockPowered(position);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CraftBlock)) return false;
        CraftBlock other = (CraftBlock) o;

        return this.position.equals(other.position) && this.getWorld().equals(other.getWorld());
    }

    @Override
    public int hashCode() {
        return this.position.hashCode() ^ this.getWorld().hashCode();
    }

    public boolean isBlockFacePowered(BlockFace face) {
        return world.getMinecraftWorld().isSidePowered(position, blockFaceToNotch(face));
    }

    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        int power = world.getMinecraftWorld().getRedstonePower(position, blockFaceToNotch(face));

        Block relative = getRelative(face);
        if (relative.getType() == Material.REDSTONE_WIRE) {
            return Math.max(power, relative.getData()) > 0;
        }

        return power > 0;
    }

    public int getBlockPower(BlockFace face) {
        int power = 0;
        BlockRedstoneWire wire = (BlockRedstoneWire) Blocks.REDSTONE_WIRE;
        net.minecraft.world.World world = this.world.getMinecraftWorld();
        int x = getX();
        int y = getY();
        int z = getZ();
        if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.isSidePowered(new BlockPos(x, y - 1, z), EnumFacing.DOWN)) power = wire.getPower(world, new BlockPos(x, y - 1, z), power);
        if ((face == BlockFace.UP || face == BlockFace.SELF) && world.isSidePowered(new BlockPos(x, y + 1, z), EnumFacing.UP)) power = wire.getPower(world, new BlockPos(x, y + 1, z), power);
        if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.isSidePowered(new BlockPos(x + 1, y, z), EnumFacing.EAST)) power = wire.getPower(world, new BlockPos(x + 1, y, z), power);
        if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.isSidePowered(new BlockPos(x - 1, y, z), EnumFacing.WEST)) power = wire.getPower(world, new BlockPos(x - 1, y, z), power);
        if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.isSidePowered(new BlockPos(x, y, z - 1), EnumFacing.NORTH)) power = wire.getPower(world, new BlockPos(x, y, z - 1), power);
        if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.isSidePowered(new BlockPos(x, y, z + 1), EnumFacing.SOUTH)) power = wire.getPower(world, new BlockPos(x, y, z - 1), power);
        return power > 0 ? power : (face == BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0;
    }

    public int getBlockPower() {
        return getBlockPower(BlockFace.SELF);
    }

    public boolean isEmpty() {
        return getType() == Material.AIR;
    }

    public boolean isLiquid() {
        return (getType() == Material.WATER) || (getType() == Material.LAVA);
    }

    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(getNMS().getPushReaction().ordinal());
    }

    private boolean itemCausesDrops(ItemStack item) {
        net.minecraft.block.Block block = this.getNMSBlock();
        net.minecraft.item.Item itemType = CraftMagicNumbers.getItem(item.getType());
        return block != null && (block.getDefaultState().getMaterial().isToolNotRequired() || (itemType != null && itemType.canHarvestBlock(block.getDefaultState())));
    }

    public boolean breakNaturally() {
        // Order matters here, need to drop before setting to air so skulls can get their data
        net.minecraft.block.Block block = this.getNMSBlock();
        boolean result = false;

        if (block != null && block != Blocks.AIR) {
            block.dropNaturally(getNMS(), world.getMinecraftWorld(), position, 1.0F, 0);
            result = true;
        }

        setType(Material.AIR);
        return result;
    }

    public boolean breakNaturally(ItemStack item) {
        if (itemCausesDrops(item)) {
            return breakNaturally();
        } else {
            return setTypeAndData(Blocks.AIR.getDefaultState(), true);
        }
    }

    public Collection<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();

        net.minecraft.block.Block block = this.getNMSBlock();
        if (block != Blocks.AIR) {
            IBlockState data = getData0();
            // based on nms.Block.dropNaturally
            int count = block.getDropCount(data, 0, world.getMinecraftWorld(), position, world.getMinecraftWorld().rand);
            for (int i = 0; i < count; ++i) {
                Item item = block.getDropType(data, world.getMinecraftWorld(), position, 0).getItem();
                if (item != Items.AIR) {
                    // Skulls are special, their data is based on the tile entity
                    if (block instanceof BlockSkullAbstract) {
                        net.minecraft.item.ItemStack nmsStack = block.a((IBlockAccess) world, position, data);
                        TileEntitySkull tileentityskull = (TileEntitySkull) world.getTileEntity(position);

                        if ((block == Blocks.PLAYER_HEAD || block == Blocks.PLAYER_WALL_HEAD) && tileentityskull.getPlayerProfile() != null) {
                            NBTTagCompound nbttagcompound = new NBTTagCompound();

                            NBTUtil.writeGameProfile(nbttagcompound, tileentityskull.getPlayerProfile());
                            nmsStack.getOrCreateTag().setTag("SkullOwner", nbttagcompound);
                        }

                        drops.add(CraftItemStack.asBukkitCopy(nmsStack));
                        // We don't want to drop cocoa blocks, we want to drop cocoa beans.
                    } else if (Blocks.COCOA == block) {
                        int age = (Integer) data.get(BlockCocoa.AGE);
                        int dropAmount = (age >= 2 ? 3 : 1);
                        for (int j = 0; j < dropAmount; ++j) {
                            drops.add(new ItemStack(Material.COCOA_BEANS, 1));
                        }
                    } else {
                        drops.add(new ItemStack(org.bukkit.craftbukkit.util.CraftMagicNumbers.getMaterial(item), 1));
                    }
                }
            }
        }
        return drops;
    }

    public Collection<ItemStack> getDrops(ItemStack item) {
        if (itemCausesDrops(item)) {
            return getDrops();
        } else {
            return Collections.emptyList();
        }
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public List<MetadataValue> getMetadata(String metadataKey) {
        return getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }
}
