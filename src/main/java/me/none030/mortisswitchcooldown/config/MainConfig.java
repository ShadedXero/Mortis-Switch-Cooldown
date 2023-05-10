package me.none030.mortisswitchcooldown.config;

import me.none030.mortisswitchcooldown.MortisSwitchCooldown;
import me.none030.mortisswitchcooldown.cooldown.CooldownManager;
import me.none030.mortisswitchcooldown.cooldown.weapons.CrackShotWeapon;
import me.none030.mortisswitchcooldown.cooldown.weapons.QualityArmoryWeapon;
import me.none030.mortisswitchcooldown.cooldown.weapons.WeaponMechanicsWeapon;
import me.none030.mortisswitchcooldown.utils.MessageUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class MainConfig {

    private final MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();
    private final CooldownManager cooldownManager;

    public MainConfig(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
        loadConfig();
    }

    private void loadConfig() {
        File file = saveConfig();
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
        cooldownManager.addMessages(loadMessages(config.getConfigurationSection("messages")));
    }

    private void loadCrackShot(ConfigurationSection crackShot) {
        if (crackShot == null) {
            return;
        }
        for (String weaponId : crackShot.getKeys(false)) {
            long coolDown = crackShot.getLong(weaponId);
            CrackShotWeapon weapon = new CrackShotWeapon(weaponId, coolDown);
            cooldownManager.getWeapons().add(weapon);
        }
    }

    private void loadWeaponMechanics(ConfigurationSection weaponMechanics) {
        if (weaponMechanics == null) {
            return;
        }
        for (String weaponId : weaponMechanics.getKeys(false)) {
            long coolDown = weaponMechanics.getLong(weaponId);
            WeaponMechanicsWeapon weapon = new WeaponMechanicsWeapon(weaponId, coolDown);
            cooldownManager.getWeapons().add(weapon);
        }
    }

    private void loadQualityArmory(ConfigurationSection qualityArmory) {
        if (qualityArmory == null) {
            return;
        }
        for (String weaponId : qualityArmory.getKeys(false)) {
            long coolDown = qualityArmory.getLong(weaponId);
            QualityArmoryWeapon weapon = new QualityArmoryWeapon(weaponId, coolDown);
            cooldownManager.getWeapons().add(weapon);
        }
    }

    public HashMap<String, String> loadMessages(ConfigurationSection messages) {
        HashMap<String, String> messageById = new HashMap<>();
        if (messages == null) {
            return messageById;
        }
        for (String key : messages.getKeys(false)) {
            String id = key.replace("-", "_").toUpperCase();
            String message = messages.getString(key);
            MessageUtils editor = new MessageUtils(message);
            messageById.put(id, editor.color());
        }
        return messageById;
    }

    private File saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        return file;
    }
}