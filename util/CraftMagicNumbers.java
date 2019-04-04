package org.bukkit.craftbukkit.util;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.advancements.AdvancementManager;
import net.minecraft.block.Block;
import net.minecraft.util.JsonUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.UnsafeValues;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.UnknownDependencyException;

@SuppressWarnings("deprecation")
public final class CraftMagicNumbers implements UnsafeValues {
    public static final UnsafeValues INSTANCE = new CraftMagicNumbers();

    private CraftMagicNumbers() {}

    public static IBlockState getBlock(MaterialData material) {
        return getBlock(material.getItemType(), material.getData());
    }

    public static IBlockState getBlock(Material material, byte data) {
        return CraftLegacy.fromLegacyData(CraftLegacy.toLegacy(material), getBlock(material), data);
    }

    public static MaterialData getMaterial(IBlockState data) {
        return CraftLegacy.toLegacy(getMaterial(data.getBlock())).getNewData(toLegacyData(data));
    }

    public static Item getItem(Material material, short data) {
        if (material.isLegacy()) {
            return CraftLegacy.fromLegacyData(CraftLegacy.toLegacy(material), getItem(material), data);
        }

        return getItem(material);
    }

    public static MaterialData getMaterialData(Item item) {
        return CraftLegacy.toLegacyData(getMaterial(item));
    }

    // ========================================================================
    private static final Map<Block, Material> BLOCK_MATERIAL = new HashMap<>();
    private static final Map<Item, Material> ITEM_MATERIAL = new HashMap<>();
    private static final Map<Material, Item> MATERIAL_ITEM = new HashMap<>();
    private static final Map<Material, Block> MATERIAL_BLOCK = new HashMap<>();

    static {
        for (Block block : (Iterable<Block>) Block.REGISTRY) { // Eclipse fail
            BLOCK_MATERIAL.put(block, Material.getMaterial(Block.REGISTRY.b(block).getKey().toUpperCase(Locale.ROOT)));
        }

        for (Item item : (Iterable<Item>) Item.REGISTRY) { // Eclipse fail
            ITEM_MATERIAL.put(item, Material.getMaterial(Item.REGISTRY.b(item).getKey().toUpperCase(Locale.ROOT)));
        }

        for (Material material : Material.values()) {
            ResourceLocation key = key(material);
            MATERIAL_ITEM.put(material, Item.REGISTRY.get(key));
            MATERIAL_BLOCK.put(material, Block.REGISTRY.get(key));
        }
    }

    public static Material getMaterial(Block block) {
        return BLOCK_MATERIAL.get(block);
    }

    public static Material getMaterial(Item item) {
        return ITEM_MATERIAL.getOrDefault(item, Material.AIR);
    }

    public static Item getItem(Material material) {
        return MATERIAL_ITEM.get(material);
    }

    public static Block getBlock(Material material) {
        return MATERIAL_BLOCK.get(material);
    }

    public static ResourceLocation key(Material mat) {
        if (mat.isLegacy()) {
            mat = CraftLegacy.fromLegacy(mat);
        }

        return CraftNamespacedKey.toMinecraft(mat.getKey());
    }
    // ========================================================================

    public static byte toLegacyData(IBlockState data) {
        return CraftLegacy.toLegacyData(data);
    }

    @Override
    public Material toLegacy(Material material) {
        return CraftLegacy.toLegacy(material);
    }

    @Override
    public Material fromLegacy(Material material) {
        return CraftLegacy.fromLegacy(material);
    }

    @Override
    public Material fromLegacy(MaterialData material) {
        return CraftLegacy.fromLegacy(material);
    }

    @Override
    public BlockData fromLegacy(Material material, byte data) {
        return CraftBlockData.fromData(getBlock(material, data));
    }

    public static final int DATA_VERSION = 1513;

    @Override
    public int getDataVersion() {
        return DATA_VERSION;
    }

    @Override
    public ItemStack modifyItemStack(ItemStack stack, String arguments) {
        net.minecraft.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        try {
            nmsStack.setTagCompound((NBTTagCompound) JsonToNBT.getTagFromJson(arguments));
        } catch (CommandSyntaxException ex) {
            Logger.getLogger(CraftMagicNumbers.class.getName()).log(Level.SEVERE, null, ex);
        }

        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));

        return stack;
    }

    @Override
    public Advancement loadAdvancement(NamespacedKey key, String advancement) {
        if (Bukkit.getAdvancement(key) != null) {
            throw new IllegalArgumentException("Advancement " + key + " already exists.");
        }

        net.minecraft.advancements.Advancement.Builder nms = (net.minecraft.advancements.Advancement.Builder) JsonUtils.gsonDeserialize(AdvancementManager.GSON, advancement, net.minecraft.advancements.Advancement.Builder.class);
        if (nms != null) {
            AdvancementManager.ADVANCEMENT_LIST.loadAdvancements(Maps.newHashMap(Collections.singletonMap(CraftNamespacedKey.toMinecraft(key), nms)));
            Advancement bukkit = Bukkit.getAdvancement(key);

            if (bukkit != null) {
                File file = new File(MinecraftServer.getServer().bukkitDataPackFolder, "data" + File.separator + key.getNamespace() + File.separator + "advancements" + File.separator + key.getKey() + ".json");
                file.getParentFile().mkdirs();

                try {
                    Files.write(advancement, file, Charsets.UTF_8);
                } catch (IOException ex) {
                    Bukkit.getLogger().log(Level.SEVERE, "Error saving advancement " + key, ex);
                }

                MinecraftServer.getServer().getPlayerList().reloadResources();

                return bukkit;
            }
        }

        return null;
    }

    @Override
    public boolean removeAdvancement(NamespacedKey key) {
        File file = new File(MinecraftServer.getServer().bukkitDataPackFolder, "data" + File.separator + key.getNamespace() + File.separator + "advancements" + File.separator + key.getKey() + ".json");
        return file.delete();
    }

    @Override
    public void checkSupported(PluginDescriptionFile pdf) {
        if (pdf.getAPIVersion() != null) {
            if (!pdf.getAPIVersion().equals("1.13")) {
                throw new UnknownDependencyException("Unsupported API version " + pdf.getAPIVersion());
            }
        }
    }

    public static boolean isLegacy(PluginDescriptionFile pdf) {
        return pdf.getAPIVersion() == null;
    }

    @Override
    public byte[] processClass(PluginDescriptionFile pdf, byte[] clazz) {
        try {
            clazz = Commodore.convert(clazz, !isLegacy(pdf));
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Fatal error trying to convert " + pdf.getFullName(), ex);
        }

        return clazz;
    }

    /**
     * This helper class represents the different NBT Tags.
     * <p>
     * These should match NBTBase#getTypeId
     */
    public static class NBT {

        public static final int TAG_END = 0;
        public static final int TAG_BYTE = 1;
        public static final int TAG_SHORT = 2;
        public static final int TAG_INT = 3;
        public static final int TAG_LONG = 4;
        public static final int TAG_FLOAT = 5;
        public static final int TAG_DOUBLE = 6;
        public static final int TAG_BYTE_ARRAY = 7;
        public static final int TAG_STRING = 8;
        public static final int TAG_LIST = 9;
        public static final int TAG_COMPOUND = 10;
        public static final int TAG_INT_ARRAY = 11;
        public static final int TAG_ANY_NUMBER = 99;
    }
}
