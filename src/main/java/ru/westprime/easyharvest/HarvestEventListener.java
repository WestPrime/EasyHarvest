package ru.westprime.easyharvest;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

// This class listens for player interactions with blocks and handles harvesting crops.
public class HarvestEventListener implements Listener {

    private final EasyHarvestPlugin plugin;

    // Constructor for HarvestEventListener.
    public HarvestEventListener(EasyHarvestPlugin plugin) {
        this.plugin = plugin;
    }

    // Event handler for player right-clicking a block.
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        // Checking the conditions.
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;
        if (!Data.hoe_list.contains(event.getPlayer().getInventory().getItemInMainHand().getType())) return;
        if (!Data.crop_list.contains(clickedBlock.getType())) return;
        if (clickedBlock.getBlockData() instanceof Ageable ageable && ageable.getAge() == ageable.getMaximumAge()) { assert true; }
        else return;
        // If execution reached this point, all conditions are met.
        // Breaking the crop.
        event.getPlayer().swingMainHand();
        clickedBlock.breakNaturally();

        // Replanting the crop.
        BukkitRunnable resetCrop = new BukkitRunnable() {
            @Override
            public void run() {
                Location blockLocation = clickedBlock.getLocation();
                Random random = new Random();

                ageable.setAge(random.nextInt(0, 3));
                clickedBlock.getWorld().setType(blockLocation, clickedBlock.getType());
                clickedBlock.getWorld().setBlockData(blockLocation, ageable);
            }
        };
        resetCrop.runTaskLater(plugin, 1);
    }
}