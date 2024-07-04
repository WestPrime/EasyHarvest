package ru.westprime.easyharvest;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Data {
    public static final Set<Material> crop_list = new HashSet<>(Arrays.asList(
            Material.WHEAT,
            Material.CARROTS,
            Material.POTATOES,
            Material.BEETROOTS,
            Material.SWEET_BERRY_BUSH,
            Material.COCOA,
            Material.NETHER_WART,
            Material.GLOW_BERRIES
    ));

    public static final Set<Material> hoe_list = new HashSet<>(Arrays.asList(
        Material.WOODEN_HOE,
        Material.STONE_HOE,
        Material.GOLDEN_HOE,
        Material.IRON_HOE,
        Material.DIAMOND_HOE,
        Material.NETHERITE_HOE
    ));
}
