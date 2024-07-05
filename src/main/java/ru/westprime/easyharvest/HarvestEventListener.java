package ru.westprime.easyharvest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HarvestEventListener implements Listener {

    private final EasyHarvest plugin;

    private List<Material> cropTypes = new ArrayList<>();
    private List<Material> toolTypes = new ArrayList<>();

    public HarvestEventListener(EasyHarvest plugin) {
        this.plugin = plugin;
        cropTypes = loadCropTypes();
        toolTypes = loadToolTypes();
    }

    private List<Material> loadCropTypes() {
        List<Material> cropTypes = new ArrayList<>();
        String[] crops = plugin.getConfig().getStringList("crops").toArray(new String[0]);
        for (String crop : crops) {
            cropTypes.add(Material.matchMaterial(crop));
        }
        return cropTypes;
    }

    private List<Material> loadToolTypes() {
        List<Material> toolTypes = new ArrayList<>();
        String[] tools = plugin.getConfig().getStringList("tools").toArray(new String[0]);
        for (String tool : tools) {
            toolTypes.add(Material.matchMaterial(tool));
        }
        return toolTypes;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) { return; }

        final Location blockLocation = clickedBlock.getLocation();
        final Material clickedBlockType = clickedBlock.getType();
        final BlockData clickedBlockData = clickedBlock.getBlockData();

        Material playerTool = event.getPlayer().getInventory().getItemInMainHand().getType();
        if (!toolTypes.contains(playerTool) || !cropTypes.contains(clickedBlock.getType()) || !isCropMature(clickedBlock)) {
            return;
        }

        event.getPlayer().swingMainHand();
        event.getPlayer().breakBlock(clickedBlock);

        if (plugin.getConfig().getBoolean("damageTool") && event.getItem() instanceof Damageable) {
            ItemMeta meta = event.getItem().getItemMeta();
            if (meta != null) {
                ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + 1);
                event.getItem().setItemMeta(meta);
            }
        }

        if (plugin.getConfig().getBoolean("giveXP")) {
            event.getPlayer().giveExp(new Random().nextInt(plugin.getConfig().getInt("minXP"), plugin.getConfig().getInt("maxXP")));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                // set block type at the block location
                blockLocation.getBlock().setType(clickedBlockType);
                blockLocation.getBlock().setBlockData(clickedBlockData);

                Ageable ageable = (Ageable) clickedBlock.getBlockData();
                if (plugin.getConfig().getBoolean("randomAge")) {
                    ageable.setAge(new Random().nextInt(4));
                }
                else {
                    ageable.setAge(0);
                }
                blockLocation.getBlock().setBlockData(ageable);
            }
        }.runTaskLater(plugin, 1);
    }

    private boolean isCropMature(Block clickedBlock) {
        Ageable ageable = (Ageable) clickedBlock.getBlockData();
        return ageable.getAge() == ageable.getMaximumAge();
    }
}