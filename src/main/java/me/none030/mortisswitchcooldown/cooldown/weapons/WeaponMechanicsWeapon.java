package me.none030.mortisswitchcooldown.cooldown.weapons;

import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.bukkit.inventory.ItemStack;

public class WeaponMechanicsWeapon extends Weapon {

    public WeaponMechanicsWeapon(String weaponId, long coolDown) {
        super(weaponId, coolDown);
    }

    @Override
    public boolean isWeapon(ItemStack weapon) {
        String weaponId = WeaponMechanicsAPI.getWeaponTitle(weapon);
        if (weaponId == null) {
            return false;
        }
        return isId(weaponId);
    }
}
