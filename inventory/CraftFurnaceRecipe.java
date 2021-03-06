package org.bukkit.craftbukkit.inventory;

import java.util.stream.Stream;
import net.minecraft.server.MinecraftServer;
import net.minecraft.item.crafting.Ingredient;

import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe {
    public CraftFurnaceRecipe(NamespacedKey key, ItemStack result, ItemStack source, float experience, int cookingTime) {
        super(key, result, source.getType(), source.getDurability(), experience, cookingTime);
    }

    public static CraftFurnaceRecipe fromBukkitRecipe(FurnaceRecipe recipe) {
        if (recipe instanceof CraftFurnaceRecipe) {
            return (CraftFurnaceRecipe) recipe;
        }
        return new CraftFurnaceRecipe(recipe.getKey(), recipe.getResult(), recipe.getInput(), recipe.getExperience(), recipe.getCookingTime());
    }

    @Override
    public void addToCraftingManager() {
        ItemStack result = this.getResult();
        Ingredient input = new Ingredient(Stream.of(new RecipeItemStack.StackProvider(CraftItemStack.asNMSCopy(this.getInput()))));

        MinecraftServer.getServer().getCraftingManager().a(new net.minecraft.server.FurnaceRecipe(CraftNamespacedKey.toMinecraft(this.getKey()), "", input, CraftItemStack.asNMSCopy(result), getExperience(), getCookingTime()));
    }
}
