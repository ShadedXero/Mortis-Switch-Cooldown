package com.mortisdevelopment.mortisswitchcooldown.manager;

import com.mortisdevelopment.mortisswitchcooldown.MortisSwitchCooldown;
import com.mortisdevelopment.mortisswitchcooldown.config.MainConfig;
import com.mortisdevelopment.mortisswitchcooldown.cooldown.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class SwitchCooldownManager {

    private final MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();
    private CooldownManager cooldownManager;
    private MainConfig mainConfig;

    public SwitchCooldownManager() {
        this.cooldownManager = new CooldownManager();
        this.mainConfig = new MainConfig(this);
        plugin.getServer().getPluginCommand("cooldown").setExecutor(new SwitchCooldownCommand(this));
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setCooldownManager(new CooldownManager());
        setMainConfig(new MainConfig(this));
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public void setCooldownManager(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public void setMainConfig(MainConfig mainConfig) {
        this.mainConfig = mainConfig;
    }
}
