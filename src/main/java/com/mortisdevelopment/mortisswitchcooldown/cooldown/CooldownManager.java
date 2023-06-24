package com.mortisdevelopment.mortisswitchcooldown.cooldown;

import com.mortisdevelopment.mortiscorepaper.managers.CoreManager;
import com.mortisdevelopment.mortisswitchcooldown.MortisSwitchCooldown;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import me.zombie_striker.qg.api.QualityArmory;
import me.zombie_striker.qg.guns.Gun;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.*;

public class CooldownManager extends CoreManager {

    public MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();
    private final Map<String, Long> csCooldownByTitle;
    private final Map<String, Long> wmCooldownByTitle;
    private final Map<String, Long> qaCooldownByTitle;
    private final Map<UUID, Long> inCooldown;

    public CooldownManager() {
        this.csCooldownByTitle = new HashMap<>();
        this.wmCooldownByTitle = new HashMap<>();
        this.qaCooldownByTitle = new HashMap<>();
        this.inCooldown = new HashMap<>();
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

    public void sendDenyMessage(@NotNull Player player) {
        DecimalFormat formatter = new DecimalFormat("#.#");
        Long value = inCooldown.get(player.getUniqueId());
        if (value == null) {
            return;
        }
        double cooldown =  (value * 5) / 20.0;
        Component component = getMessage("DENY_MESSAGE").replaceText(TextReplacementConfig.builder().match("%cooldown%").replacement(formatter.format(cooldown)).once().build());
        player.sendMessage(component);
    }

    public String getCSWeaponTitle(@NotNull ItemStack weapon) {
        return plugin.getCrackShot().getWeaponTitle(weapon);
    }

    public String getWMWeaponTitle(@NotNull ItemStack weapon) {
        return WeaponMechanicsAPI.getWeaponTitle(weapon);
    }

    public String getQAWeaponTitle(@NotNull ItemStack weapon) {
        Gun gun = QualityArmory.getGun(weapon);
        if (gun == null) {
            return null;
        }
        return gun.getName();
    }

    public void addCooldown(@NotNull Player player, long cooldown, int slot) {
        inCooldown.put(player.getUniqueId(), cooldown);
        new BukkitRunnable() {
            long ticks = cooldown;

            @Override
            public void run() {
                ticks -= 1;
                inCooldown.put(player.getUniqueId(), ticks);
                if (player.getInventory().getHeldItemSlot() != slot) {
                    inCooldown.remove(player.getUniqueId());
                    cancel();
                }
                if (ticks <= 0) {
                    inCooldown.remove(player.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }

    public Set<String> getCsTitles() {
        return new HashSet<>(csCooldownByTitle.keySet());
    }
    public Set<String> getWmTitles() {
        return new HashSet<>(wmCooldownByTitle.keySet());
    }

    public Set<String> getQaTitles() {
        return new HashSet<>(qaCooldownByTitle.keySet());
    }

    public Map<String, Long> getCsCooldownByTitle() {
        return csCooldownByTitle;
    }

    public Map<String, Long> getWmCooldownByTitle() {
        return wmCooldownByTitle;
    }

    public Map<String, Long> getQaCooldownByTitle() {
        return qaCooldownByTitle;
    }

    public Map<UUID, Long> getInCooldown() {
        return inCooldown;
    }
}
