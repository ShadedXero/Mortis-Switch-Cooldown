package me.none030.weaponswitchcooldown;

import com.shampaggon.crackshot.CSUtility;
import me.none030.weaponswitchcooldown.cooldown.CooldownManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeaponSwitchCooldown extends JavaPlugin implements Listener {

    private static WeaponSwitchCooldown Instance;
    private static CSUtility CrackShot;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        CrackShot = new CSUtility();
        cooldownManager = new CooldownManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static WeaponSwitchCooldown getInstance() {
        return Instance;
    }

    public static CSUtility getCrackShot() {
        return CrackShot;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
