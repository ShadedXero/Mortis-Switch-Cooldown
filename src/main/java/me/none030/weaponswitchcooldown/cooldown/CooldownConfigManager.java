package me.none030.weaponswitchcooldown.cooldown;

import me.none030.weaponswitchcooldown.WeaponSwitchCooldown;
import me.none030.weaponswitchcooldown.utils.MessageUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CooldownConfigManager {

    public static String DenyMessage;
    public WeaponSwitchCooldown plugin = WeaponSwitchCooldown.getInstance();

    private final List<Weapon> weapons;

    public CooldownConfigManager() {
        weapons = new ArrayList<>();
        loadConfig();
    }

    private void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("weapons");
        if (section == null) {
            plugin.getLogger().severe("Could not find weapons section in config.yml");
            return;
        }
        for (String key : section.getKeys(false)) {
            long cooldown = section.getLong(key + ".cooldown");
            Weapon weapon = new Weapon(key, cooldown);
            weapons.add(weapon);
        }
        DenyMessage = MessageUtils.colorMessage(config.getString("deny-message"));
    }

    private File saveConfig() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", true);
        }
        return file;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }
}
