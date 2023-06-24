package com.mortisdevelopment.mortisswitchcooldown;

import com.mortisdevelopment.mortiscorepaper.MortisCorePaper;
import com.mortisdevelopment.mortisswitchcooldown.manager.SwitchCooldownManager;
import com.shampaggon.crackshot.CSUtility;
import org.bukkit.plugin.java.JavaPlugin;

public final class MortisSwitchCooldown extends JavaPlugin {

    private static MortisSwitchCooldown Instance;
    private CSUtility crackShot;
    private boolean weaponMechanics;
    private boolean qualityArmory;
    private SwitchCooldownManager switchCooldownManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        MortisCorePaper.register(this);
        if (getServer().getPluginManager().getPlugin("CrackShot") != null) {
            crackShot = new CSUtility();
        }
        weaponMechanics = getServer().getPluginManager().getPlugin("WeaponMechanics") != null;
        qualityArmory = getServer().getPluginManager().getPlugin("QualityArmory") != null;
        switchCooldownManager = new SwitchCooldownManager();
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

    public SwitchCooldownManager getSwitchCooldownManager() {
        return switchCooldownManager;
    }
}
