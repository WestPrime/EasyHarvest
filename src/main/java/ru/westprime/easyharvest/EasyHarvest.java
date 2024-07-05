package ru.westprime.easyharvest;

import org.bukkit.plugin.java.JavaPlugin;

public final class EasyHarvest extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Check if config.yml exists. If not, create it.
        this.saveDefaultConfig();
        saveConfig();
        // Register event listener
        final HarvestEventListener hel = new HarvestEventListener(this);
        getServer().getPluginManager().registerEvents(hel, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
