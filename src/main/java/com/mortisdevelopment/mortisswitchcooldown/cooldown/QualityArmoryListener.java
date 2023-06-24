package com.mortisdevelopment.mortisswitchcooldown.cooldown;

import me.zombie_striker.qg.api.QAWeaponPrepareShootEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class QualityArmoryListener implements Listener {

    private final CooldownManager cooldownManager;

    public QualityArmoryListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onShoot(QAWeaponPrepareShootEvent e) {
        Player player = e.getPlayer();
        if (!cooldownManager.getInCooldown().containsKey(player.getUniqueId())) {
            return;
        }
        cooldownManager.sendDenyMessage(player);
        e.setCancelled(true);
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        ItemStack weapon = player.getInventory().getItem(e.getNewSlot());
        if (weapon == null || weapon.getType().isAir()) {
            return;
        }
        String weaponTitle = cooldownManager.getQAWeaponTitle(weapon);
        if (weaponTitle == null || !cooldownManager.getQaTitles().contains(weaponTitle)) {
            return;
        }
        long cooldown = cooldownManager.getQaCooldownByTitle().get(weaponTitle);
        cooldownManager.addCooldown(player, cooldown, e.getNewSlot());
    }
}