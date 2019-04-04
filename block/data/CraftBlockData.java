package org.bukkit.craftbukkit.block.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.ArgumentBlock;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.IStringSerializable;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public class CraftBlockData implements BlockData {

    private IBlockState state;

    protected CraftBlockData() {
        throw new AssertionError("Template Constructor");
    }

    protected CraftBlockData(IBlockState state) {
        this.state = state;
    }

    @Override
    public Material getMaterial() {
        return CraftMagicNumbers.getMaterial(state.getBlock());
    }

    public IBlockState getState() {
        return state;
    }

    /**
     * Get a given BlockStateEnum's value as its Bukkit counterpart.
     *
     * @param nms the NMS state to convert
     * @param bukkit the Bukkit class
     * @param <B> the type
     * @return the matching Bukkit type
     */
    protected <B extends Enum<B>> B get(PropertyEnum<?> nms, Class<B> bukkit) {
        return toBukkit(state.get(nms), bukkit);
    }

    /**
     * Convert all values from the given BlockStateEnum to their appropriate
     * Bukkit counterpart.
     *
     * @param nms the NMS state to get values from
     * @param bukkit the bukkit class to convert the values to
     * @param <B> the bukkit class type
     * @return an immutable Set of values in their appropriate Bukkit type
     */
    @SuppressWarnings("unchecked")
    protected <B extends Enum<B>> Set<B> getValues(PropertyEnum<?> nms, Class<B> bukkit) {
        ImmutableSet.Builder<B> values = ImmutableSet.builder();

        for (Enum<?> e : nms.d()) {
            values.add(toBukkit(e, bukkit));
        }

        return values.build();
    }

    /**
     * Set a given {@link BlockStateEnum} with the matching enum from Bukkit.
     *
     * @param nms the NMS BlockStateEnum to set
     * @param bukkit the matching Bukkit Enum
     * @param <B> the Bukkit type
     * @param <N> the NMS type
     */
    protected <B extends Enum<B>, N extends Enum<N> & IStringSerializable> void set(PropertyEnum<N> nms, Enum<B> bukkit) {
        this.state = this.state.set(nms, toNMS(bukkit, nms.getValueClass()));
    }

    private static final BiMap<Enum<?>, Enum<?>> nmsToBukkit = HashBiMap.create();

    /**
     * Convert an NMS Enum (usually a BlockStateEnum) to its appropriate Bukkit
     * enum from the given class.
     *
     * @throws IllegalStateException if the Enum could not be converted
     */
    @SuppressWarnings("unchecked")
    private static <B extends Enum<B>> B toBukkit(Enum<?> nms, Class<B> bukkit) {
        Enum<?> converted = nmsToBukkit.get(nms);
        if (converted != null) {
            return (B) converted;
        }

        if (nms instanceof EnumFacing) {
            converted = CraftBlock.notchToBlockFace((EnumFacing) nms);
        } else {
            converted = bukkit.getEnumConstants()[nms.ordinal()];
        }

        Preconditions.checkState(converted != null, "Could not convert enum %s->%s", nms, bukkit);
        nmsToBukkit.put(nms, converted);

        return (B) converted;
    }

    /**
     * Convert a given Bukkit enum to its matching NMS enum type.
     *
     * @param bukkit the Bukkit enum to convert
     * @param nms the NMS class
     * @return the matching NMS type
     * @throws IllegalStateException if the Enum could not be converted
     */
    @SuppressWarnings("unchecked")
    private static <N extends Enum<N> & IStringSerializable> N toNMS(Enum<?> bukkit, Class<N> nms) {
        Enum<?> converted = nmsToBukkit.inverse().get(bukkit);
        if (converted != null) {
            return (N) converted;
        }

        if (bukkit instanceof BlockFace) {
            converted = CraftBlock.blockFaceToNotch((BlockFace) bukkit);
        } else {
            converted = nms.getEnumConstants()[bukkit.ordinal()];
        }

        Preconditions.checkState(converted != null, "Could not convert enum %s->%s", nms, bukkit);
        nmsToBukkit.put(converted, bukkit);

        return (N) converted;
    }

    /**
     * Get the current value of a given state.
     *
     * @param ibs the state to check
     * @param <T> the type
     * @return the current value of the given state
     */
    protected <T extends Comparable<T>> T get(IProperty<T> ibs) {
        // Straight integer or boolean getter
        return this.state.get(ibs);
    }

    /**
     * Set the specified state's value.
     *
     * @param ibs the state to set
     * @param v the new value
     * @param <T> the state's type
     * @param <V> the value's type. Must match the state's type.
     */
    public <T extends Comparable<T>, V extends T> void set(IProperty<T> ibs, V v) {
        // Straight integer or boolean setter
        this.state = this.state.set(ibs, v);
    }

    @Override
    public String getAsString() {
        return state.toString();
    }

    @Override
    public BlockData clone() {
        try {
            return (BlockData) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError("Clone not supported", ex);
        }
    }

    @Override
    public String toString() {
        return "CraftBlockData{" + state.toString() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CraftBlockData && state.equals(((CraftBlockData) obj).state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    protected static PropertyBool getBoolean(String name) {
        throw new AssertionError("Template Method");
    }

    protected static PropertyBool getBoolean(String name, boolean optional) {
        throw new AssertionError("Template Method");
    }

    protected static PropertyEnum<?> getEnum(String name) {
        throw new AssertionError("Template Method");
    }

    protected static PropertyInteger getInteger(String name) {
        throw new AssertionError("Template Method");
    }

    protected static PropertyBool getBoolean(Class<? extends Block> block, String name) {
        return (PropertyBool) getState(block, name, false);
    }

    protected static PropertyBool getBoolean(Class<? extends Block> block, String name, boolean optional) {
        return (PropertyBool) getState(block, name, optional);
    }

    protected static PropertyEnum<?> getEnum(Class<? extends Block> block, String name) {
        return (PropertyEnum<?>) getState(block, name, false);
    }

    protected static PropertyInteger getInteger(Class<? extends Block> block, String name) {
        return (PropertyInteger) getState(block, name, false);
    }

    /**
     * Get a specified {@link IBlockState} from a given block's class with a
     * given name
     *
     * @param block the class to retrieve the state from
     * @param name the name of the state to retrieve
     * @param optional if the state can be null
     * @return the specified state or null
     * @throws IllegalStateException if the state is null and {@code optional}
     * is false.
     */
    private static IProperty<?> getState(Class<? extends Block> block, String name, boolean optional) {
        IProperty<?> state = null;

        for (Block instance : (Iterable<Block>) Block.REGISTRY) { // Eclipse fail
            if (instance.getClass() == block) {
                if (state == null) {
                    state = instance.getStates().getProperty(name);
                } else {
                    IProperty<?> newState = instance.getStates().getProperty(name);

                    Preconditions.checkState(state == newState, "State mistmatch %s,%s", state, newState);
                }
            }
        }

        Preconditions.checkState(optional || state != null, "Null state for %s,%s", block, name);

        return state;
    }

    /**
     * Get the minimum value allowed by the BlockStateInteger.
     *
     * @param state the state to check
     * @return the minimum value allowed
     */
    protected static int getMin(PropertyInteger state) {
        return state.min;
    }

    /**
     * Get the maximum value allowed by the BlockStateInteger.
     *
     * @param state the state to check
     * @return the maximum value allowed
     */
    protected static int getMax(PropertyInteger state) {
        return state.max;
    }

    //
    private static final Map<Class<? extends Block>, Class<? extends CraftBlockData>> MAP = new HashMap<>();

    static {
        register(net.minecraft.block.BlockAnvil.class, org.bukkit.craftbukkit.block.impl.CraftAnvil.class);
        register(net.minecraft.block.BlockBanner.class, org.bukkit.craftbukkit.block.impl.CraftBanner.class);
        register(net.minecraft.server.BlockBannerWall.class, org.bukkit.craftbukkit.block.impl.CraftBannerWall.class);
        register(net.minecraft.block.BlockBed.class, org.bukkit.craftbukkit.block.impl.CraftBed.class);
        register(net.minecraft.block.BlockBeetroot.class, org.bukkit.craftbukkit.block.impl.CraftBeetroot.class);
        register(net.minecraft.block.BlockBrewingStand.class, org.bukkit.craftbukkit.block.impl.CraftBrewingStand.class);
        register(net.minecraft.server.BlockBubbleColumn.class, org.bukkit.craftbukkit.block.impl.CraftBubbleColumn.class);
        register(net.minecraft.block.BlockCactus.class, org.bukkit.craftbukkit.block.impl.CraftCactus.class);
        register(net.minecraft.block.BlockCake.class, org.bukkit.craftbukkit.block.impl.CraftCake.class);
        register(net.minecraft.block.BlockCarrot.class, org.bukkit.craftbukkit.block.impl.CraftCarrots.class);
        register(net.minecraft.block.BlockCauldron.class, org.bukkit.craftbukkit.block.impl.CraftCauldron.class);
        register(net.minecraft.block.BlockChest.class, org.bukkit.craftbukkit.block.impl.CraftChest.class);
        register(net.minecraft.server.BlockChestTrapped.class, org.bukkit.craftbukkit.block.impl.CraftChestTrapped.class);
        register(net.minecraft.block.BlockChorusFlower.class, org.bukkit.craftbukkit.block.impl.CraftChorusFlower.class);
        register(net.minecraft.block.BlockChorusPlant.class, org.bukkit.craftbukkit.block.impl.CraftChorusFruit.class);
        register(net.minecraft.block.BlockWall.class, org.bukkit.craftbukkit.block.impl.CraftCobbleWall.class);
        register(net.minecraft.block.BlockCocoa.class, org.bukkit.craftbukkit.block.impl.CraftCocoa.class);
        register(net.minecraft.block.BlockCommandBlock.class, org.bukkit.craftbukkit.block.impl.CraftCommand.class);
        register(net.minecraft.server.BlockCoralFan.class, org.bukkit.craftbukkit.block.impl.CraftCoralFan.class);
        register(net.minecraft.block.BlockCrops.class, org.bukkit.craftbukkit.block.impl.CraftCrops.class);
        register(net.minecraft.block.BlockDaylightDetector.class, org.bukkit.craftbukkit.block.impl.CraftDaylightDetector.class);
        register(net.minecraft.server.BlockDirtSnow.class, org.bukkit.craftbukkit.block.impl.CraftDirtSnow.class);
        register(net.minecraft.block.BlockDispenser.class, org.bukkit.craftbukkit.block.impl.CraftDispenser.class);
        register(net.minecraft.block.BlockDoor.class, org.bukkit.craftbukkit.block.impl.CraftDoor.class);
        register(net.minecraft.block.BlockDropper.class, org.bukkit.craftbukkit.block.impl.CraftDropper.class);
        register(net.minecraft.block.BlockEndRod.class, org.bukkit.craftbukkit.block.impl.CraftEndRod.class);
        register(net.minecraft.block.BlockEnderChest.class, org.bukkit.craftbukkit.block.impl.CraftEnderChest.class);
        register(net.minecraft.block.BlockEndPortalFrame.class, org.bukkit.craftbukkit.block.impl.CraftEnderPortalFrame.class);
        register(net.minecraft.block.BlockFence.class, org.bukkit.craftbukkit.block.impl.CraftFence.class);
        register(net.minecraft.block.BlockFenceGate.class, org.bukkit.craftbukkit.block.impl.CraftFenceGate.class);
        register(net.minecraft.block.BlockFire.class, org.bukkit.craftbukkit.block.impl.CraftFire.class);
        register(net.minecraft.block.BlockStandingSign.class, org.bukkit.craftbukkit.block.impl.CraftFloorSign.class);
        register(net.minecraft.block.BlockLiquid.class, org.bukkit.craftbukkit.block.impl.CraftFluids.class);
        register(net.minecraft.block.BlockFurnace.class, org.bukkit.craftbukkit.block.impl.CraftFurnace.class);
        register(net.minecraft.server.BlockGlassPane.class, org.bukkit.craftbukkit.block.impl.CraftGlassPane.class);
        register(net.minecraft.block.BlockGlazedTerracotta.class, org.bukkit.craftbukkit.block.impl.CraftGlazedTerracotta.class);
        register(net.minecraft.block.BlockGrass.class, org.bukkit.craftbukkit.block.impl.CraftGrass.class);
        register(net.minecraft.block.BlockHay.class, org.bukkit.craftbukkit.block.impl.CraftHay.class);
        register(net.minecraft.block.BlockHopper.class, org.bukkit.craftbukkit.block.impl.CraftHopper.class);
        register(net.minecraft.block.BlockHugeMushroom.class, org.bukkit.craftbukkit.block.impl.CraftHugeMushroom.class);
        register(net.minecraft.block.BlockFrostedIce.class, org.bukkit.craftbukkit.block.impl.CraftIceFrost.class);
        register(net.minecraft.server.BlockIronBars.class, org.bukkit.craftbukkit.block.impl.CraftIronBars.class);
        register(net.minecraft.block.BlockJukebox.class, org.bukkit.craftbukkit.block.impl.CraftJukeBox.class);
        register(net.minecraft.server.BlockKelp.class, org.bukkit.craftbukkit.block.impl.CraftKelp.class);
        register(net.minecraft.block.BlockLadder.class, org.bukkit.craftbukkit.block.impl.CraftLadder.class);
        register(net.minecraft.block.BlockLeaves.class, org.bukkit.craftbukkit.block.impl.CraftLeaves.class);
        register(net.minecraft.block.BlockLever.class, org.bukkit.craftbukkit.block.impl.CraftLever.class);
        register(net.minecraft.block.BlockLog.class, org.bukkit.craftbukkit.block.impl.CraftLogAbstract.class);
        register(net.minecraft.block.BlockRailDetector.class, org.bukkit.craftbukkit.block.impl.CraftMinecartDetector.class);
        register(net.minecraft.block.BlockRail.class, org.bukkit.craftbukkit.block.impl.CraftMinecartTrack.class);
        register(net.minecraft.block.BlockMycelium.class, org.bukkit.craftbukkit.block.impl.CraftMycel.class);
        register(net.minecraft.block.BlockNetherWart.class, org.bukkit.craftbukkit.block.impl.CraftNetherWart.class);
        register(net.minecraft.block.BlockNote.class, org.bukkit.craftbukkit.block.impl.CraftNote.class);
        register(net.minecraft.block.BlockObserver.class, org.bukkit.craftbukkit.block.impl.CraftObserver.class);
        register(net.minecraft.block.BlockPistonBase.class, org.bukkit.craftbukkit.block.impl.CraftPiston.class);
        register(net.minecraft.block.BlockPistonExtension.class, org.bukkit.craftbukkit.block.impl.CraftPistonExtension.class);
        register(net.minecraft.block.BlockPistonMoving.class, org.bukkit.craftbukkit.block.impl.CraftPistonMoving.class);
        register(net.minecraft.block.BlockPortal.class, org.bukkit.craftbukkit.block.impl.CraftPortal.class);
        register(net.minecraft.block.BlockPotato.class, org.bukkit.craftbukkit.block.impl.CraftPotatoes.class);
        register(net.minecraft.block.BlockRailPowered.class, org.bukkit.craftbukkit.block.impl.CraftPoweredRail.class);
        register(net.minecraft.block.BlockPressurePlate.class, org.bukkit.craftbukkit.block.impl.CraftPressurePlateBinary.class);
        register(net.minecraft.block.BlockPressurePlateWeighted.class, org.bukkit.craftbukkit.block.impl.CraftPressurePlateWeighted.class);
        register(net.minecraft.server.BlockPumpkinCarved.class, org.bukkit.craftbukkit.block.impl.CraftPumpkinCarved.class);
        register(net.minecraft.block.BlockRedstoneComparator.class, org.bukkit.craftbukkit.block.impl.CraftRedstoneComparator.class);
        register(net.minecraft.block.BlockRedstoneLight.class, org.bukkit.craftbukkit.block.impl.CraftRedstoneLamp.class);
        register(net.minecraft.block.BlockRedstoneOre.class, org.bukkit.craftbukkit.block.impl.CraftRedstoneOre.class);
        register(net.minecraft.block.BlockRedstoneTorch.class, org.bukkit.craftbukkit.block.impl.CraftRedstoneTorch.class);
        register(net.minecraft.server.BlockRedstoneTorchWall.class, org.bukkit.craftbukkit.block.impl.CraftRedstoneTorchWall.class);
        register(net.minecraft.block.BlockRedstoneWire.class, org.bukkit.craftbukkit.block.impl.CraftRedstoneWire.class);
        register(net.minecraft.block.BlockReed.class, org.bukkit.craftbukkit.block.impl.CraftReed.class);
        register(net.minecraft.block.BlockRedstoneRepeater.class, org.bukkit.craftbukkit.block.impl.CraftRepeater.class);
        register(net.minecraft.block.BlockRotatedPillar.class, org.bukkit.craftbukkit.block.impl.CraftRotatable.class);
        register(net.minecraft.block.BlockSapling.class, org.bukkit.craftbukkit.block.impl.CraftSapling.class);
        register(net.minecraft.server.BlockSeaPickle.class, org.bukkit.craftbukkit.block.impl.CraftSeaPickle.class);
        register(net.minecraft.block.BlockShulkerBox.class, org.bukkit.craftbukkit.block.impl.CraftShulkerBox.class);
        register(net.minecraft.block.BlockSkull.class, org.bukkit.craftbukkit.block.impl.CraftSkull.class);
        register(net.minecraft.server.BlockSkullPlayer.class, org.bukkit.craftbukkit.block.impl.CraftSkullPlayer.class);
        register(net.minecraft.server.BlockSkullPlayerWall.class, org.bukkit.craftbukkit.block.impl.CraftSkullPlayerWall.class);
        register(net.minecraft.server.BlockSkullWall.class, org.bukkit.craftbukkit.block.impl.CraftSkullWall.class);
        register(net.minecraft.block.BlockSnow.class, org.bukkit.craftbukkit.block.impl.CraftSnow.class);
        register(net.minecraft.block.BlockFarmland.class, org.bukkit.craftbukkit.block.impl.CraftSoil.class);
        register(net.minecraft.block.BlockStainedGlassPane.class, org.bukkit.craftbukkit.block.impl.CraftStainedGlassPane.class);
        register(net.minecraft.block.BlockStairs.class, org.bukkit.craftbukkit.block.impl.CraftStairs.class);
        register(net.minecraft.block.BlockStem.class, org.bukkit.craftbukkit.block.impl.CraftStem.class);
        register(net.minecraft.server.BlockStemAttached.class, org.bukkit.craftbukkit.block.impl.CraftStemAttached.class);
        register(net.minecraft.block.BlockSlab.class, org.bukkit.craftbukkit.block.impl.CraftStepAbstract.class);
        register(net.minecraft.block.BlockButtonStone.class, org.bukkit.craftbukkit.block.impl.CraftStoneButton.class);
        register(net.minecraft.block.BlockStructure.class, org.bukkit.craftbukkit.block.impl.CraftStructure.class);
        register(net.minecraft.server.BlockTallPlantFlower.class, org.bukkit.craftbukkit.block.impl.CraftTallPlantFlower.class);
        register(net.minecraft.server.BlockTallPlantShearable.class, org.bukkit.craftbukkit.block.impl.CraftTallPlantShearable.class);
        register(net.minecraft.server.BlockTallSeaGrass.class, org.bukkit.craftbukkit.block.impl.CraftTallSeaGrass.class);
        register(net.minecraft.server.BlockTorchWall.class, org.bukkit.craftbukkit.block.impl.CraftTorchWall.class);
        register(net.minecraft.block.BlockTrapDoor.class, org.bukkit.craftbukkit.block.impl.CraftTrapdoor.class);
        register(net.minecraft.block.BlockTripWire.class, org.bukkit.craftbukkit.block.impl.CraftTripwire.class);
        register(net.minecraft.block.BlockTripWireHook.class, org.bukkit.craftbukkit.block.impl.CraftTripwireHook.class);
        register(net.minecraft.server.BlockTurtleEgg.class, org.bukkit.craftbukkit.block.impl.CraftTurtleEgg.class);
        register(net.minecraft.block.BlockVine.class, org.bukkit.craftbukkit.block.impl.CraftVine.class);
        register(net.minecraft.block.BlockWallSign.class, org.bukkit.craftbukkit.block.impl.CraftWallSign.class);
        register(net.minecraft.server.BlockWitherSkull.class, org.bukkit.craftbukkit.block.impl.CraftWitherSkull.class);
        register(net.minecraft.server.BlockWitherSkullWall.class, org.bukkit.craftbukkit.block.impl.CraftWitherSkullWall.class);
        register(net.minecraft.block.BlockButtonWood.class, org.bukkit.craftbukkit.block.impl.CraftWoodButton.class);
    }

    private static void register(Class<? extends Block> nms, Class<? extends CraftBlockData> bukkit) {
        Preconditions.checkState(MAP.put(nms, bukkit) == null, "Duplicate mapping %s->%s", nms, bukkit);
    }

    public static CraftBlockData newData(Material material, String data) {
        IBlockState blockData;
        Block block = CraftMagicNumbers.getBlock(material);

        // Data provided, use it
        if (data != null) {
            try {
                // Material provided, force that material in
                if (block != null) {
                    data = Block.REGISTRY.b(block) + data;
                }

                StringReader reader = new StringReader(data);
                ArgumentBlock arg = new ArgumentBlock(reader, false).a(false);
                Preconditions.checkArgument(!reader.canRead(), "Spurious trailing data");

                blockData = arg.b();
            } catch (CommandSyntaxException ex) {
                throw new IllegalArgumentException("Could not parse data: " + data, ex);
            }
        } else {
            blockData = block.getDefaultState();
        }

        return fromData(blockData);
    }

    public static CraftBlockData fromData(IBlockState data) {
        Class<? extends CraftBlockData> craft = MAP.get(data.getBlock().getClass());
        if (craft == null) {
            craft = CraftBlockData.class;
        }

        CraftBlockData ret;
        try {
            ret = craft.getDeclaredConstructor(IBlockState.class).newInstance(data);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }

        return ret;
    }
}
