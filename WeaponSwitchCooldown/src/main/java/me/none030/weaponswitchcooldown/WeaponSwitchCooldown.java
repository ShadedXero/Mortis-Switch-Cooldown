package me.none030.weaponswitchcooldown;

import me.none030.weaponswitchcooldown.events.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeaponSwitchCooldown extends JavaPlugin {

    public static Plugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginManager().registerEvents(new Event(), this);
        this.saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
