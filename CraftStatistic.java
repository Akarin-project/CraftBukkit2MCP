package org.bukkit.craftbukkit;

import net.minecraft.stats.StatList;

import org.bukkit.Statistic;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;

public enum CraftStatistic {
    DAMAGE_DEALT("damage_dealt"),
    DAMAGE_TAKEN("damage_taken"),
    DEATHS("deaths"),
    MOB_KILLS("mob_kills"),
    PLAYER_KILLS("player_kills"),
    FISH_CAUGHT("fish_caught"),
    ANIMALS_BRED("animals_bred"),
    LEAVE_GAME("leave_game"),
    JUMP("jump"),
    DROP_COUNT("drop"),
    DROP("dropped"),
    PICKUP("picked_up"),
    PLAY_ONE_MINUTE("play_one_minute"),
    WALK_ONE_CM("walk_one_cm"),
    WALK_ON_WATER_ONE_CM("walk_on_water_one_cm"),
    FALL_ONE_CM("fall_one_cm"),
    SNEAK_TIME("sneak_time"),
    CLIMB_ONE_CM("climb_one_cm"),
    FLY_ONE_CM("fly_one_cm"),
    WALK_UNDER_WATER_ONE_CM("walk_under_water_one_cm"),
    MINECART_ONE_CM("minecart_one_cm"),
    BOAT_ONE_CM("boat_one_cm"),
    PIG_ONE_CM("pig_one_cm"),
    HORSE_ONE_CM("horse_one_cm"),
    SPRINT_ONE_CM("sprint_one_cm"),
    CROUCH_ONE_CM("crouch_one_cm"),
    AVIATE_ONE_CM("aviate_one_cm"),
    MINE_BLOCK("mined"),
    USE_ITEM("used"),
    BREAK_ITEM("broken"),
    CRAFT_ITEM("crafted"),
    KILL_ENTITY("killed"),
    ENTITY_KILLED_BY("killed_by"),
    TIME_SINCE_DEATH("time_since_death"),
    TALKED_TO_VILLAGER("talked_to_villager"),
    TRADED_WITH_VILLAGER("traded_with_villager"),
    CAKE_SLICES_EATEN("eat_cake_slice"),
    CAULDRON_FILLED("fill_cauldron"),
    CAULDRON_USED("use_cauldron"),
    ARMOR_CLEANED("clean_armor"),
    BANNER_CLEANED("clean_banner"),
    BREWINGSTAND_INTERACTION("interact_with_brewingstand"),
    BEACON_INTERACTION("interact_with_beacon"),
    DROPPER_INSPECTED("inspect_dropper"),
    HOPPER_INSPECTED("inspect_hopper"),
    DISPENSER_INSPECTED("inspect_dispenser"),
    NOTEBLOCK_PLAYED("play_noteblock"),
    NOTEBLOCK_TUNED("tune_noteblock"),
    FLOWER_POTTED("pot_flower"),
    TRAPPED_CHEST_TRIGGERED("trigger_trapped_chest"),
    ENDERCHEST_OPENED("open_enderchest"),
    ITEM_ENCHANTED("enchant_item"),
    RECORD_PLAYED("play_record"),
    FURNACE_INTERACTION("interact_with_furnace"),
    CRAFTING_TABLE_INTERACTION("interact_with_crafting_table"),
    CHEST_OPENED("open_chest"),
    SLEEP_IN_BED("sleep_in_bed"),
    SHULKER_BOX_OPENED("open_shulker_box"),
    TIME_SINCE_REST("time_since_rest"),
    SWIM_ONE_CM("swim_one_cm");
    private final ResourceLocation minecraftKey;
    private final org.bukkit.Statistic bukkit;
    private static final BiMap<ResourceLocation, org.bukkit.Statistic> statistics;

    static {
        ImmutableBiMap.Builder<ResourceLocation, org.bukkit.Statistic> statisticBuilder = ImmutableBiMap.builder();
        for (CraftStatistic statistic : CraftStatistic.values()) {
            statisticBuilder.put(statistic.minecraftKey, statistic.bukkit);
        }

        statistics = statisticBuilder.build();
    }

    private CraftStatistic(String minecraftKey) {
        this.minecraftKey = new ResourceLocation(minecraftKey);

        this.bukkit = org.bukkit.Statistic.valueOf(this.name());
        Preconditions.checkState(bukkit != null, "Bukkit statistic %s does not exist", this.name());
    }

    public static org.bukkit.Statistic getBukkitStatistic(net.minecraft.stats.StatBase<?> statistic) {
        RegistryNamespaced statRegistry = statistic.a().a();
        ResourceLocation nmsKey = StatList.REGISTRY.b(statistic.a());

        if (statRegistry == StatList.REGISTRY_CUSTOM) {
            nmsKey = (ResourceLocation) statistic.b();
        }

        return statistics.get(nmsKey);
    }

    public static net.minecraft.stats.StatBase getNMSStatistic(org.bukkit.Statistic bukkit) {
        Preconditions.checkArgument(bukkit.getType() == Statistic.Type.UNTYPED, "This method only accepts untyped statistics");

        net.minecraft.stats.StatBase<ResourceLocation> nms = StatList.CUSTOM.b(statistics.inverse().get(bukkit));
        Preconditions.checkArgument(nms != null, "NMS Statistic %s does not exist", bukkit);

        return nms;
    }

    public static net.minecraft.stats.StatBase getMaterialStatistic(org.bukkit.Statistic stat, Material material) {
        try {
            if (stat == Statistic.MINE_BLOCK) {
                return StatList.BLOCK_MINED.b(CraftMagicNumbers.getBlock(material));
            }
            if (stat == Statistic.CRAFT_ITEM) {
                return StatList.ITEM_CRAFTED.b(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.USE_ITEM) {
                return StatList.ITEM_USED.b(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.BREAK_ITEM) {
                return StatList.ITEM_BROKEN.b(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.PICKUP) {
                return StatList.ITEM_PICKED_UP.b(CraftMagicNumbers.getItem(material));
            }
            if (stat == Statistic.DROP) {
                return StatList.ITEM_DROPPED.b(CraftMagicNumbers.getItem(material));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

    public static net.minecraft.stats.StatBase getEntityStatistic(org.bukkit.Statistic stat, EntityType entity) {
        if (entity.getName() != null) {
            EntityList<?> nmsEntity = EntityList.REGISTRY.get(new ResourceLocation(entity.getName()));

            if (stat == org.bukkit.Statistic.KILL_ENTITY) {
                return net.minecraft.stats.StatList.ENTITY_KILLED.b(nmsEntity);
            }
            if (stat == org.bukkit.Statistic.ENTITY_KILLED_BY) {
                return net.minecraft.stats.StatList.ENTITY_KILLED_BY.b(nmsEntity);
            }
        }
        return null;
    }

    public static EntityType getEntityTypeFromStatistic(net.minecraft.stats.StatBase<EntityList<?>> statistic) {
        ResourceLocation name = EntityList.getName(statistic.b());
        return EntityType.fromName(name.getResourcePath());
    }

    public static Material getMaterialFromStatistic(net.minecraft.stats.StatBase<?> statistic) {
        if (statistic.b() instanceof Item) {
            return CraftMagicNumbers.getMaterial((Item) statistic.b());
        }
        if (statistic.b() instanceof Block) {
            return CraftMagicNumbers.getMaterial((Block) statistic.b());
        }
        return null;
    }
}
