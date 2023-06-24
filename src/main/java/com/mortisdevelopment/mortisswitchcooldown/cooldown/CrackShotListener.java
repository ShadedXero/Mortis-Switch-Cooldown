package com.mortisdevelopment.mortisswitchcooldown.cooldown;

import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class CrackShotListener implements Listener {

    private final CooldownManager cooldownManager;

    public CrackShotListener(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @EventHandler
    public void onShoot(WeaponPrepareShootEvent e) {
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
        String weaponTitle = cooldownManager.getCSWeaponTitle(weapon);
        if (weaponTitle == null || !cooldownManager.getCsTitles().contains(weaponTitle)) {
            return;
        }
        long cooldown = cooldownManager.getCsCooldownByTitle().get(weaponTitle);
        System.out.println(cooldown);
        cooldownManager.addCooldown(player, cooldown, e.getNewSlot());
    }
}
