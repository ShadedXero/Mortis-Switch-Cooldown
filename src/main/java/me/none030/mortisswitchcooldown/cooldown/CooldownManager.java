package me.none030.mortisswitchcooldown.cooldown;

import me.none030.mortisswitchcooldown.MortisSwitchCooldown;
import me.none030.mortisswitchcooldown.config.MainConfig;
import me.none030.mortisswitchcooldown.cooldown.listeners.CrackShotListener;
import me.none030.mortisswitchcooldown.cooldown.listeners.QualityArmoryListener;
import me.none030.mortisswitchcooldown.cooldown.listeners.WeaponMechanicsListener;
import me.none030.mortisswitchcooldown.cooldown.weapons.CrackShotWeapon;
import me.none030.mortisswitchcooldown.cooldown.weapons.QualityArmoryWeapon;
import me.none030.mortisswitchcooldown.cooldown.weapons.Weapon;
import me.none030.mortisswitchcooldown.cooldown.weapons.WeaponMechanicsWeapon;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CooldownManager extends Manager{

    public MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();
    private List<Weapon> weapons;
    private MainConfig config;
    private HashMap<UUID, Long> inCoolDown;

    public CooldownManager() {
        this.weapons = new ArrayList<>();
        this.config = new MainConfig(this);
        this.inCoolDown = new HashMap<>();
        if (plugin.hasCrackShot()) {
            plugin.getServer().getPluginManager().registerEvents(new CrackShotListener(this), plugin);
        }
        if (plugin.hasWeaponMechanics()) {
            plugin.getServer().getPluginManager().registerEvents(new WeaponMechanicsListener(this), plugin);
        }
        if (plugin.hasQualityArmory()) {
            plugin.getServer().getPluginManager().registerEvents(new QualityArmoryListener(this), plugin);
        }
        plugin.getServer().getPluginCommand("cooldown").setExecutor(new CooldownCommand(this));
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        this.weapons = new ArrayList<>();
        this.config = new MainConfig(this);
        this.inCoolDown = new HashMap<>();
        if (plugin.hasCrackShot()) {
            plugin.getServer().getPluginManager().registerEvents(new CrackShotListener(this), plugin);
        }
        if (plugin.hasWeaponMechanics()) {
            plugin.getServer().getPluginManager().registerEvents(new WeaponMechanicsListener(this), plugin);
        }
        if (plugin.hasQualityArmory()) {
            plugin.getServer().getPluginManager().registerEvents(new QualityArmoryListener(this), plugin);
        }
    }

    public List<CrackShotWeapon> getCrackShotWeapons() {
        List<CrackShotWeapon> crackShotWeapons = new ArrayList<>();
        for (Weapon weapon : weapons) {
            if (weapon instanceof CrackShotWeapon) {
                crackShotWeapons.add((CrackShotWeapon) weapon);
            }
        }
        return crackShotWeapons;
    }

    public List<WeaponMechanicsWeapon> getWeaponMechanicsWeapons() {
        List<WeaponMechanicsWeapon> weaponMechanicsWeapons = new ArrayList<>();
        for (Weapon weapon : weapons) {
            if (weapon instanceof WeaponMechanicsWeapon) {
                weaponMechanicsWeapons.add((WeaponMechanicsWeapon) weapon);
            }
        }
        return weaponMechanicsWeapons;
    }

    public List<QualityArmoryWeapon> getQualityArmoryWeapons() {
        List<QualityArmoryWeapon> qualityArmoryWeapons = new ArrayList<>();
        for (Weapon weapon : weapons) {
            if (weapon instanceof QualityArmoryWeapon) {
                qualityArmoryWeapons.add((QualityArmoryWeapon) weapon);
            }
        }
        return qualityArmoryWeapons;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public MainConfig getConfig() {
        return config;
    }

    public HashMap<UUID, Long> getInCoolDown() {
        return inCoolDown;
    }
}
