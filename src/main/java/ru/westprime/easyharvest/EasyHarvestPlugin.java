package ru.westprime.easyharvest;

import org.bukkit.plugin.java.JavaPlugin;

public final class EasyHarvestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("EH initialized!");
        final HarvestEventListener hel = new HarvestEventListener(this);
        getServer().getPluginManager().registerEvents(hel, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
