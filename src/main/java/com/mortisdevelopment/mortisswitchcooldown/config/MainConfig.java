package com.mortisdevelopment.mortisswitchcooldown.config;

import com.mortisdevelopment.mortiscorepaper.configs.Config;
import com.mortisdevelopment.mortisswitchcooldown.MortisSwitchCooldown;
import com.mortisdevelopment.mortisswitchcooldown.manager.SwitchCooldownManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MainConfig extends Config {

    private final MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();
    private final SwitchCooldownManager manager;

    public MainConfig(SwitchCooldownManager manager) {
        super("config.yml");
        this.manager = manager;
        loadConfig();
    }

    @Override
    public void loadConfig() {
        saveConfig();
        File file = getFile();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (plugin.hasCrackShot()) {
            loadCrackShot(config.getConfigurationSection("crack-shot"));
        }
        if (plugin.hasWeaponMechanics()) {
            loadWeaponMechanics(config.getConfigurationSection("weapon-mechanics"));
        }
        if (plugin.hasQualityArmory()) {
            loadQualityArmory(config.getConfigurationSection("quality-armory"));
        }
        manager.getCooldownManager().addMessages(loadMessages(config.getConfigurationSection("messages")));
    }

    private void loadCrackShot(ConfigurationSection crackShot) {
        if (crackShot == null) {
            return;
        }
        for (String weaponTitle : crackShot.getKeys(false)) {
            long cooldown = crackShot.getLong(weaponTitle);
            manager.getCooldownManager().getCsCooldownByTitle().put(weaponTitle, cooldown);
        }
    }

    private void loadWeaponMechanics(ConfigurationSection weaponMechanics) {
        if (weaponMechanics == null) {
            return;
        }
        for (String weaponTitle : weaponMechanics.getKeys(false)) {
            long cooldown = weaponMechanics.getLong(weaponTitle);
            manager.getCooldownManager().getWmCooldownByTitle().put(weaponTitle, cooldown);
        }
    }

    private void loadQualityArmory(ConfigurationSection qualityArmory) {
        if (qualityArmory == null) {
            return;
        }
        for (String weaponTitle : qualityArmory.getKeys(false)) {
            long cooldown = qualityArmory.getLong(weaponTitle);
            manager.getCooldownManager().getQaCooldownByTitle().put(weaponTitle, cooldown);
        }
    }
}