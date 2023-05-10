package me.none030.mortisswitchcooldown.cooldown.weapons;

import me.none030.mortisswitchcooldown.MortisSwitchCooldown;
import org.bukkit.inventory.ItemStack;

public class CrackShotWeapon extends Weapon {

    private final MortisSwitchCooldown plugin = MortisSwitchCooldown.getInstance();

    public CrackShotWeapon(String weaponId, long coolDown) {
        super(weaponId, coolDown);
    }

    @Override
    public boolean isWeapon(ItemStack weapon) {
        String weaponId = plugin.getCrackShot().getWeaponTitle(weapon);
        if (weaponId == null) {
            return false;
        }
        return isId(weaponId);
    }
}
