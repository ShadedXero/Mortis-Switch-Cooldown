package me.none030.weaponswitchcooldown.cooldown;

import me.none030.weaponswitchcooldown.WeaponSwitchCooldown;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    public WeaponSwitchCooldown plugin = WeaponSwitchCooldown.getInstance();

    private final CooldownConfigManager cooldownConfigManager;
    private final HashMap<UUID, Long> inCooldown;

    public CooldownManager() {
        inCooldown = new HashMap<>();
        this.cooldownConfigManager = new CooldownConfigManager();
        plugin.getServer().getPluginManager().registerEvents(new CooldownListener(), plugin);
    }

    public CooldownConfigManager getCooldownConfigManager() {
        return cooldownConfigManager;
    }

    public HashMap<UUID, Long> getInCooldown() {
        return inCooldown;
    }
}
