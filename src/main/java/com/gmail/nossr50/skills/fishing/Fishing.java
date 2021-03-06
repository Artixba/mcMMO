package com.gmail.nossr50.skills.fishing;

import com.gmail.nossr50.config.AdvancedConfig;
import com.gmail.nossr50.config.treasure.TreasureConfig;
import com.gmail.nossr50.datatypes.treasure.ShakeTreasure;
import com.gmail.nossr50.util.Misc;
import com.gmail.nossr50.util.adapter.BiomeAdapter;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public final class Fishing {

    protected static final HashMap<Material, List<Enchantment>> ENCHANTABLE_CACHE = new HashMap<Material, List<Enchantment>>();

    public static int fishermansDietRankLevel1 = AdvancedConfig.getInstance().getFishermanDietRankChange();
    public static int fishermansDietRankLevel2 = fishermansDietRankLevel1 * 2;
    public static int fishermansDietMaxLevel   = fishermansDietRankLevel1 * 5;

    public static Set<Biome> masterAnglerBiomes = BiomeAdapter.WATER_BIOMES;
    public static Set<Biome> iceFishingBiomes   = BiomeAdapter.ICE_BIOMES;

    private Fishing() {}

    /**
     * Finds the possible drops of an entity
     *
     * @param target
     *            Targeted entity
     * @return possibleDrops List of ItemStack that can be dropped
     */
    protected static List<ShakeTreasure> findPossibleDrops(LivingEntity target) {
        if (TreasureConfig.getInstance().shakeMap.containsKey(target.getType()))
            return TreasureConfig.getInstance().shakeMap.get(target.getType());

        return null;
    }

    /**
     * Randomly chooses a drop among the list
     *
     * @param possibleDrops
     *            List of ItemStack that can be dropped
     * @return Chosen ItemStack
     */
    protected static ItemStack chooseDrop(List<ShakeTreasure> possibleDrops) {
        int dropProbability = Misc.getRandom().nextInt(100);
        double cumulatedProbability = 0;

        for (ShakeTreasure treasure : possibleDrops) {
            cumulatedProbability += treasure.getDropChance();

            if (dropProbability < cumulatedProbability) {
                return treasure.getDrop().clone();
            }
        }

        return null;
    }
}
