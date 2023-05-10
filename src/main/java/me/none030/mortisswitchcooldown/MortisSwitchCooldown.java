package me.none030.mortisswitchcooldown;

import com.shampaggon.crackshot.CSUtility;
import me.none030.mortisswitchcooldown.cooldown.CooldownManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MortisSwitchCooldown extends JavaPlugin {

    private static MortisSwitchCooldown Instance;
    private CSUtility crackShot;
    private boolean weaponMechanics;
    private boolean qualityArmory;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        if (getServer().getPluginManager().getPlugin("CrackShot") != null) {
            crackShot = new CSUtility();
        }
        weaponMechanics = getServer().getPluginManager().getPlugin("WeaponMechanics") != null;
        qualityArmory = getServer().getPluginManager().getPlugin("QualityArmory") != null;
        cooldownManager = new CooldownManager();
    }

    public static MortisSwitchCooldown getInstance() {
        return Instance;
    }

    public CSUtility getCrackShot() {
        return crackShot;
    }

    public boolean hasCrackShot() {
        return crackShot != null;
    }

    public boolean hasWeaponMechanics() {
        return weaponMechanics;
    }

    public boolean hasQualityArmory() {
        return qualityArmory;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
