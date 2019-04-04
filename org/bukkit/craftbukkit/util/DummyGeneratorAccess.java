package org.bukkit.craftbukkit.util;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.biome.Biome;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.server.Fluid;
import net.minecraft.server.FluidType;
import net.minecraft.server.GeneratorAccess;
import net.minecraft.server.HeightMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.IChunkAccess;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.server.ParticleParam;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.server.TickList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.server.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.WorldProvider;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DummyGeneratorAccess implements GeneratorAccess {

    public static final GeneratorAccess INSTANCE = new DummyGeneratorAccess();

    private DummyGeneratorAccess() {
    }

    @Override
    public long getSeed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TickList<Block> I() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TickList<FluidType> H() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IChunkAccess c(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public World getMinecraftWorld() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WorldInfo getWorldData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DifficultyInstance getDamageScaler(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T extends WorldSavedData> T a(Function<String, T> fnctn, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MapStorage s_() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void a(String string, WorldSavedData pb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IChunkProvider getChunkProvider() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ISaveHandler getDataManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Random m() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(BlockPos bp, Block block) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BlockPos getSpawn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void a(EntityPlayer eh, BlockPos bp, SoundEvent se, SoundCategory sc, float f, float f1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addParticle(ParticleParam pp, double d, double d1, double d2, double d3, double d4, double d5) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Biome getBiome(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getBrightness(EnumSkyBlock esb, BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getLightLevel(BlockPos bp, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isChunkLoaded(int i, int i1, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean e(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int a(HeightMap.Type type, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EntityPlayer a(double d, double d1, double d2, double d3, Predicate<Entity> prdct) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int c() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WorldBorder getWorldBorder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean a(Entity entity, VoxelShape vs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Entity> getEntities(Entity entity, AxisAlignedBB aabb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int a(BlockPos bp, EnumFacing ed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean e() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getSeaLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public WorldProvider o() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TileEntity getTileEntity(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IBlockState getBlockState(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Fluid b(BlockPos bp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setTypeAndData(BlockPos blockposition, IBlockState iblockdata, int i) {
        return false;
    }

    @Override
    public boolean addEntity(Entity entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setAir(BlockPos blockposition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void a(EnumSkyBlock enumskyblock, BlockPos blockposition, int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setAir(BlockPos blockposition, boolean flag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
